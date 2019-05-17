package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Use this annotation to mark a service class as a of type "Query". This is to
 * enforce CQRS pattern implementation in service classes to separate out the
 * command and query based operations. Refer to
 * <a href="https://microservices.io/patterns/data/cqrs.html">CQRS pattern</a>.
 * A query service does not do write operation with the data access and do only.
 * A QueryService annotation class can inject another QueryService, one or more
 * ApplicationService, FrameworkService or Repository, but cannot inject a
 * CommandService. query or read operations.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@CqrsService
//@Transactional(readOnly = true)
public @interface QueryService {

	@AliasFor(annotation = CqrsService.class)
	String value() default "";

	@AliasFor(annotation = CqrsService.class)
	boolean stateful() default false;
}
