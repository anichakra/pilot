package me.anichakra.poc.pilot.framework.rule.impl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.annotation.Validated;

/**
 * Use this annotation to
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ConfigurationProperties(prefix="rule")
@Validated
public @interface RuleConfig {

	/**
	 * The name prefix of the properties that are valid to bind to this object. Synonym
	 * for {@link #prefix()}. A valid prefix is defined by one or more words separated
	 * with dots (e.g. {@code "acme.system.feature"}).
	 * @return the name prefix of the properties to bind
	 */
	@AliasFor(value="prefix", annotation=ConfigurationProperties.class)
	String value() default "";

	/**
	 * The name prefix of the properties that are valid to bind to this object. Synonym
	 * for {@link #value()}. A valid prefix is defined by one or more words separated with
	 * dots (e.g. {@code "acme.system.feature"}).
	 * @return the name prefix of the properties to bind
	 */
	@AliasFor(value="value",  annotation=ConfigurationProperties.class)
	String prefix() default "";


}
