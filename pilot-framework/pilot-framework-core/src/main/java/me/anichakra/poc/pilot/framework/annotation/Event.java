package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Use this annotation to mark a service class which does not do any database
 * operations but other operations like calling external systems or processing
 * complex business logic. A Controller, a CommandService and a QueryService can
 * inject an ApplicationService. An ApplicationService annotated class cannot
 * inject CommandService, QueryService or Repository. It can only inject another
 * ApplicationService or FrameworkService or both.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Event {


	@AliasFor("name")
	String[] value() default {};

	@AliasFor("value")
	String[] name() default {};

	EventObject object() default EventObject.RESPONSE;
	
	

}
