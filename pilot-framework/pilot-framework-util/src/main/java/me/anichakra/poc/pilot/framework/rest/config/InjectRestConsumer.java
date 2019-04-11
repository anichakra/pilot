package me.anichakra.poc.pilot.framework.rest.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AliasFor;

/**
 * Use this annotation to do dependency injection of an instance as a field or
 * attribute in a class. The latter class should be either a controller or a
 * service which should be also annotated appropriately.
 * 
 * @see Injectable
 * @see Stateful
 * @see Stateless
 * @see DataAccess
 * @author anirbanchakraborty
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Autowired
@Qualifier
@Inherited
public @interface InjectRestConsumer {
	@AliasFor(annotation = Qualifier.class)
	String value();

}