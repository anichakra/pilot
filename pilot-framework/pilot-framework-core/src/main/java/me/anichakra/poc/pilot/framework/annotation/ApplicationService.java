package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
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
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@CqrsService
public @interface ApplicationService {

	@AliasFor(annotation = CqrsService.class)

	/**
	 * If the service class has a name then mark that as part of the value in the
	 * annotation.
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * Mention if the service class is stateful. By default all service classes are
	 * stateless. Stateful classes will keep session specific attributes.
	 * 
	 * @return
	 */
	@AliasFor(annotation = CqrsService.class)

	boolean stateful() default false;

}
