package me.anichakra.poc.pilot.framework.test.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class AbstractControllerTest {

    public AbstractControllerTest() {
        super();
        
    }
    
    private ApiMock mock;
    
    @Autowired
    WebApplicationContext context;

    @Before
    public void setup() {
        mock = new ApiMock(MockMvcBuilders.webAppContextSetup(context).build());
    }

    protected ResultActions getApiMock(String api) throws Exception {
       return mock.getMock().perform(get(api)).andDo(print());
    }

    public ResultActions postApiMock(String api) throws Exception {
      return mock.getMock().perform(post(api));
    }
    
}