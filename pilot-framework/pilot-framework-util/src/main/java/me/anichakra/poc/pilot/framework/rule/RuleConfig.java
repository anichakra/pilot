package me.anichakra.poc.pilot.framework.rule;

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
 * Use this annotation to annotate a rule configuration.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ConfigurationProperties(prefix = "rule")
@Validated
public @interface RuleConfig {

	@AliasFor(value = "prefix", annotation = ConfigurationProperties.class)
	String value() default "";

	@AliasFor(value = "value", annotation = ConfigurationProperties.class)
	String prefix() default "";

}
