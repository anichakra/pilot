package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to mark a service class which are part of framework and not ideally a business service or part of CQRS.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FrameworkService {

	/**
	 * If the service class has a name then mark that as part of the value in the annotation.
	 * 
	 * @return
	 */
	String value() default "";
}
