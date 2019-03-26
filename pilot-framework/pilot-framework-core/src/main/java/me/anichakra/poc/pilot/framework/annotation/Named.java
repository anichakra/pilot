package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AliasFor;

/**
 * Mark this annotation during injection of a service or a data access which has a name mentioned during creation.
 * 
 * @see InjectService
 * @see InjectDataAccess
 * @see Injectable
 * @author anirbanchakraborty
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface Named {

	/**
	 * The name of the component. If this is mentioned then during injection, the
	 * same name need to be mentioned.
	 * 
	 * @return
	 */
	@AliasFor("name")
	String[] value() default {};

	@AliasFor("value")
	String[] name() default {};

}
