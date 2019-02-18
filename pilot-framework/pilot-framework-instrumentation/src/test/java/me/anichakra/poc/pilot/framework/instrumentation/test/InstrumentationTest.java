package me.anichakra.poc.pilot.framework.instrumentation.test;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.aspect.InstrumentationConfig;
import me.anichakra.poc.pilot.framework.instrumentation.aspect.InstrumentationFilter;
import me.anichakra.poc.pilot.framework.instrumentation.mock.controller.MockController;
import me.anichakra.poc.pilot.framework.instrumentation.mock.controller.TestConfig;
import me.anichakra.poc.pilot.framework.instrumentation.mock.service.api.MockBusinessException;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTestRunner;

@MicroserviceTest(classes = { TestConfig.class,
        InstrumentationConfig.class })
@RunWith(MicroserviceTestRunner.class)

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)

public class InstrumentationTest {

    private static final String URI = "/context/mockService";
    @Autowired
    MockController testController;
    @Autowired
    InstrumentationFilter filter;
    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @Before
    public void setup() {
        request = new MockHttpServletRequest("doPost", URI);
        request.setRemoteAddr("10.10.10.10");
        request.setRemoteHost("10.10.10.10");
        request.setRemoteUser("anichakra1");
        request.setRequestURI(URI);
        request.setUserPrincipal(new Principal() {

            @Override
            public String getName() {
                return "anirban";
            }
        });
        MockHttpSession session = new MockHttpSession() {
            @Override
            public String getId() {
                return "101010101010";
            }
        };
        request.setSession(session);
        response = new MockHttpServletResponse();
    }

    @Test
    public void test_normal() {
        MockFilterChain chain = getMockFilterChain("Sample Controller Data");
        try {
            filter.doFilter(request, response, chain);
        } catch (IOException e) {
            Assert.fail();
        } catch (ServletException e) {
            Assert.fail();
        }
       
    }

    @Test
    public void test_mockSession() {
        MockHttpServletRequest request = new MockHttpServletRequest("doPost", URI) {
            @Override
            public HttpSession getSession(boolean flag) {
                return new MockHttpSession() {
                    public String getId() {
                        return "MOCK_SESSION_ID";
                    }
                };
            }
        };
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = getMockFilterChain("Sample Controller Data");
        try {
            filter.doFilter(request, response, chain);
        } catch (IOException e) {
            Assert.fail();
        } catch (ServletException e) {
            Assert.fail();
        }
      
    }

    @Test
    public void test_exception() {
        MockHttpServletRequest request = new MockHttpServletRequest("doPost", URI);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = getMockFilterChain("null");
        try {
            filter.doFilter(request, response, chain);
        } catch (IOException e) {
            Assert.fail();
        } catch (ServletException e) {
            Assert.fail();
        }
       
    }

    public void instrument_failed() {
        try {
            testController.callControllerService("null");
            Assert.assertNull(InvocationEvent.getCurrent());
        } catch (MockBusinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void instrument_complete() {
        try {
            testController.callControllerService("Sample Controller Data");
        } catch (MockBusinessException e) {
            e.printStackTrace();
        }
    }

    @Test
	public void test_normal_else() {
		MockFilterChain chain = getMockFilterChain("Sample Controller Data");
		try {
			ServletRequest requestB = Mockito.mock(ServletRequest.class);
			filter.doFilter(requestB, response, chain);
		} catch (IOException e) {
			Assert.fail();
		} catch (ServletException e) {
			Assert.fail();
		}
		
	}

	@Test
	public void test_destroy() {
		filter.destroy();
	}

	@Test
	public void test_init() throws ServletException {
		FilterConfig filterConfig =Mockito.mock(FilterConfig.class);
		filter.init(filterConfig);
	}
    
    private MockFilterChain getMockFilterChain(final String data) {
        MockFilterChain chain = new MockFilterChain() {
            public void doFilter(ServletRequest request, ServletResponse response) {
                // super.doFilter(request, response);
                try {

                    testController.callControllerService(data);

                } catch (Exception e) {
                    if (data.length() < 5) {
                        Assert.assertEquals(e.getMessage(),
                                "Input cannot be less than 5 charecters");
                    } else {
                        Assert.fail();
                    }
                }
            }
        };
        return chain;
    }
    

}
