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
 * configuration classes are required if mocking of a service or repository bean
 * is required, that is injected somewhere in the dependency chain.
 * 
 * @author anirbanchakraborty
 *
 */
public @interface MicroserviceTestConfiguration {

}
