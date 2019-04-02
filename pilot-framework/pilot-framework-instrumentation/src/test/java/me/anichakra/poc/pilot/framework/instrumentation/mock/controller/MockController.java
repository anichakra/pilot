package me.anichakra.poc.pilot.framework.instrumentation.mock.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockRequest;
import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockResponse;
import me.anichakra.poc.pilot.framework.instrumentation.mock.service.api.MockBusinessException;
import me.anichakra.poc.pilot.framework.instrumentation.mock.service.api.MockService;
@Component
public class MockController {
	
	private static final Logger logger = LogManager.getLogger();

	private MockService mockService;

	public Integer callControllerService(String testData)
			throws MockBusinessException {
		MockRequest request = new MockRequest();
		request.setInput(testData);
		MockResponse testResponse = mockService.callServiceMethod(request);
		if (logger.isDebugEnabled()) {
			logger.debug("Inside TestController.callControllerMethod()");
		}
		return testResponse.getOutput();
	}

	@Autowired
	@Qualifier("valid")
	public void setMockService(MockService mockService) {
		this.mockService = mockService;
	}
}
