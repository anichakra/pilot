package me.anichakra.poc.pilot.framework.instrumentation.mock.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.instrumentation.mock.repository.MockDao;

@Component
public class MockDaoImpl implements MockDao {
	private static final Logger logger = LogManager.getLogger();

	public Integer callDaoMethod(String string) {
		try {
			Thread.currentThread();
			Thread.sleep(300l);
			if (logger.isDebugEnabled()) {
				logger.debug("Inside TestDaoImpl.callDaoMethod()");
			}
		} catch (InterruptedException e) {
			// Intentional
		}
		return string.length();
	}
	
	public void callMarkIgnoreMethod() {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside TestDaoImpl.callMarkIgnoreMethod()");
		}
	}
}
