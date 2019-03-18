package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;

/**
 * Mark this annotation in a method which returns an instance that is required
 * to be injected in some other component with @Inject annotation.
 * 
 * @see Inject
 * @author anirbanchakraborty
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Bean
public @interface Injectable {

	@AliasFor("name")
	String[] value() default {};

	@AliasFor("value")
	String[] name() default {};

}
