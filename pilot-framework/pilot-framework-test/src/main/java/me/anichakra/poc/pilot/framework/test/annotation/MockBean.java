package me.anichakra.poc.pilot.framework.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Primary
@Bean

/**
 * Apply this annotation on all methods MicroserviceTestConfiguration to load a
 * mock bean in the line of dependency wiring.
 * 
 * @author anirbanchakraborty
 *
 */
public @interface MockBean {
	/**
	 * Alias for {@link #name}.
	 * <p>
	 * Intended to be used when no other attributes are needed, for example:
	 * {@code @Bean("customBeanName")}.
	 * 
	 * @since 4.3.3
	 * @see #name
	 */
	@AliasFor("name")
	String[] value() default {};

	/**
	 * The name of this bean, or if several names, a primary bean name plus aliases.
	 * <p>
	 * If left unspecified, the name of the bean is the name of the annotated
	 * method. If specified, the method name is ignored.
	 * <p>
	 * The bean name and aliases may also be configured via the {@link #value}
	 * attribute if no other attributes are declared.
	 * 
	 * @see #value
	 */
	@AliasFor("value")
	String[] name() default {};
}
