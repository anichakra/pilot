package me.anichakra.poc.pilot.framework.test.annotation;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class MicroserviceTestRunner extends SpringJUnit4ClassRunner {

    /**
     * Construct a new {@code SpringRunner} and initialize a
     * {@link org.springframework.test.context.TestContextManager TestContextManager}
     * to provide Spring testing functionality to standard JUnit 4 tests.
     * @param clazz the test class to be run
     * @see #createTestContextManager(Class)
     */
    public MicroserviceTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}