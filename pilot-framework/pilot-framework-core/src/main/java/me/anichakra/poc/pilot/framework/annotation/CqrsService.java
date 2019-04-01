package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

/**
 * Use this annotation to mark a service class as a of type "Command". This is
 * to enforce CQRS pattern implementation in service classes to separate out the
 * command and query based operations. Refer to
 * <a href="https://microservices.io/patterns/data/cqrs.html">CQRS pattern</a>.
 * A command service does write operation with the data access and do not do
 * query operations. All methods in a command service are transactional in nature.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public abstract @interface CqrsService {

	@AliasFor(annotation = Service.class)
	String value() default "";

	boolean stateful() default false;

}
