package me.anichakra.poc.pilot.framework.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc

/**
 * The test class should be annotated with this annotation, so that the test
 * framework can identify the class and run it.
 * 
 * @author anirbanchakraborty
 *
 */
public @interface MicroserviceTest {

	Class<?>[] classes() default {};
}
