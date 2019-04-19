package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * A method can be marked with this annotation to indicate that this method is
 * special and is part of event-driven actions. The name of the event must be
 * provided. The object is optional, default is {@link EventObject}.REQUEST.
 * When set to REQUEST then the method argument will be used if set to RESPONSE
 * then the return value of the method will be used.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Event {

	@AliasFor("name")
	String[] value() default {};

	@AliasFor("value")
	String[] name() default {};

	EventObject object() default EventObject.REQUEST;
}
