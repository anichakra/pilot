package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to mark a service class which is orchestrating multiple
 * {@link CommandService} or {@link QueryService} annotated classes and is complex. In that
 * case the controller should directly invoke the application service class. 
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

	/**
	 * Mention if the service class is stateful. By default all service classes are
	 * stateless. Stateful classes will keep session specific attributes. If a
	 * service is stateless but keeps attribute which are not annotated as either
	 * {@link FrameworkService}, {@link CommandService}, {@link QueryService} or
	 * {@link DataAccess} then the class will fail to load.
	 * 
	 * @return
	 */
	boolean stateful() default false;

}
