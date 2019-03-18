package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.annotation.AliasFor;

/**
 * Provide this annotation on the main class of a microservice application. This
 * is a mandatory annotation.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication(scanBasePackages = "me.anichakra.poc.pilot")
@EntityScan(basePackages = "me.anichakra.poc.pilot")
public @interface Microservice {
	/**
	 * Exclude specific auto-configuration classes such that they will never be
	 * applied.
	 * 
	 * @return the classes to exclude
	 */
	@AliasFor(annotation = EnableAutoConfiguration.class)
	Class<?>[] exclude() default {};
}
