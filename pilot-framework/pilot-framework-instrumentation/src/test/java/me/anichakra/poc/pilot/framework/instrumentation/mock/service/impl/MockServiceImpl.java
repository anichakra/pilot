package me.anichakra.poc.pilot.framework.instrumentation.mock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockRequest;
import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockResponse;
import me.anichakra.poc.pilot.framework.instrumentation.mock.service.api.MockBusinessException;
import me.anichakra.poc.pilot.framework.instrumentation.mock.service.api.MockService;
import me.anichakra.poc.pilot.instrumentation.mock.repository.MockDao;

@Component(value="valid")
public class MockServiceImpl implements MockService {

	private MockDao mockDao;

	public MockResponse callServiceMethod(MockRequest request)
			throws MockBusinessException {
		int response = 0;

		try {
			Thread.currentThread();
			Thread.sleep(200l);
			response = mockDao.callDaoMethod(request.getInput());
			if (response < 5) {
				throw new MockBusinessException(
						"Input cannot be less than 5 charecters");
			}
		} catch (InterruptedException e) {
			throw new RuntimeException("Some Runtime Exception has occured!", e);
		}
		for (int i = 0; i < 5; i++) {
			mockDao.callMarkIgnoreMethod();
		}
		MockResponse mockResponse = new MockResponse();
		mockResponse.setOutput(response);
		return mockResponse;
	}

	@Autowired
	public void setMockDao(MockDao mockDao) {
		this.mockDao = mockDao;
	}

}
