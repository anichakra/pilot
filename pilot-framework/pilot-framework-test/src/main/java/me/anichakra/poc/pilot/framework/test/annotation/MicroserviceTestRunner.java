package me.anichakra.poc.pilot.framework.test.annotation;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This abstracts the spring JUnit runner.
 * @author anirbanchakraborty
 *
 */
public class MicroserviceTestRunner extends SpringJUnit4ClassRunner {

    public MicroserviceTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}