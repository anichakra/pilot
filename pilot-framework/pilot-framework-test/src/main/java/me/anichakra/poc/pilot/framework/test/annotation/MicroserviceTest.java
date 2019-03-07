package me.anichakra.poc.pilot.framework.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
@AutoConfigureMockMvc

/**
 * Apply this annotation on all Test classes. This opinionates a microservice environment.
 * @author anirbanchakraborty
 *
 */
public @interface MicroserviceTest {

	
    Class<?>[] classes() default {};
}
