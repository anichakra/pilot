package me.anichakra.poc.pilot.framework.test.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;

@Component
@WebAppConfiguration
public class MockMicroserviceEnvironment {  
    
    @Autowired
    private MockMvc mockMvc;
    
    //@Autowired
    //private WebApplicationContext context;

    
    //public MockMicroserviceEnvironment() {
      //  mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    //}
    
    public MockMvc getMockMvc() {
        return mockMvc;
    }
}
