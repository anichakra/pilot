package me.anichakra.poc.pilot.framework.test.api;

import org.springframework.test.web.servlet.MockMvc;

public class ApiMock {

    private MockMvc mock;

    public MockMvc getMock() {
        return mock;
    }

    public void setMock(MockMvc mock) {
        this.mock = mock;
    }

    public ApiMock(MockMvc mock) {
this.mock = mock    ;}

}
