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
 * Each microservice might need to mock any of the layers, for example external
 * rest call, or data-source. To do that create one class and annotate with this
 * annotation. To mock any implementation use @MockInjectable on the method
 * which returns the mock instance.
 * 
 * @author anirbanchakraborty
 *
 */
public @interface MockConfiguration {

}
