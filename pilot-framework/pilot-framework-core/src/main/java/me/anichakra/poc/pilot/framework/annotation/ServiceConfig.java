package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Use this annotation to mark a service class which are part of framework and not ideally a business service or part of CQRS.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@ConfigurationProperties("service")
@FrameworkService
@Validated
@Documented
public @interface ServiceConfig {

}
