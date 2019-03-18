package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Use this annotation to mark a service class as a stateless business service.
 * If state is kept in the class then this class will not be loaded and the
 * microservice will not be started. Make sure all attributes in this class is
 * marked with @Inject.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface StatelessService {

	@AliasFor(annotation = Component.class)
	String value() default "";
}
