package me.anichakra.poc.pilot.framework.instrumentation.mock.service.api;

import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockRequest;
import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockResponse;

public interface MockService {
  MockResponse callServiceMethod(MockRequest request) throws MockBusinessException;
}
