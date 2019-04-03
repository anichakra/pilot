package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use this annotation to mark a service class as a of type "Command". This is
 * to enforce CQRS pattern implementation in service classes to separate out the
 * command and query based operations. Refer to
 * <a href="https://microservices.io/patterns/data/cqrs.html">CQRS pattern</a>.
 * A command service does write operation with the data access and do not do
 * query operations. All methods in a command service are transactional and
 * non-readonly in nature. A CommandService annotated class can inject one or
 * more CommandService, ApplicationService FrameworkService and Repository. A CommandService cannot inject a QueryService.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@CqrsService
@Transactional(readOnly = false)
public @interface CommandService {

	@AliasFor(annotation = CqrsService.class)
	String value() default "";

	@AliasFor(annotation = CqrsService.class)
	boolean stateful() default false;

}
