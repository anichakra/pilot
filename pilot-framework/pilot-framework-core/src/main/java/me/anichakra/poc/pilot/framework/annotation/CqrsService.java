package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

/**
 * Abstract annotation to be inherited by {@link CommandService} and {@link QueryService}. Refer to
 * <a href="https://microservices.io/patterns/data/cqrs.html">CQRS pattern</a>.
 * 
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
