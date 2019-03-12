package me.anichakra.poc.pilot.framework.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Profile("test")
@Configuration

/**
 * Annotate with this on all microservice test configuration classes. Test
 * configuration classes are required if mocking is required in any of the beans
 * autowired in any of the layers starting from controller to repositoru.
 * 
 * @author anirbanchakraborty
 *
 */
public @interface MicroserviceTestConfiguration {

}
