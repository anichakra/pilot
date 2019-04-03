package me.anichakra.poc.pilot.framework.test.impl;

import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This abstracts the {@link SpringJUnit4ClassRunner}] and must be passes as an
 * argument to JUnit annotation @RunWith.
 * 
 * @see RunWith
 * @author anirbanchakraborty
 *
 */
public class MicroserviceTestRunner extends SpringJUnit4ClassRunner {

	public MicroserviceTestRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

}