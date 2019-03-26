package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Repository;

/**
 * Use this annotation to mark a component, i.e. a class or an interface as data
 * access. All components that access a database should be marked as @DataAccess.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
public @interface DataAccess {

	@AliasFor(annotation = Repository.class)
	String value() default "";
}
