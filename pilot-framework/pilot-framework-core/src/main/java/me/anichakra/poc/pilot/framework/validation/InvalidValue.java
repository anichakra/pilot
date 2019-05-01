package me.anichakra.poc.pilot.framework.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate in any field of any exception class to indicate that that this field
 * contains the invalid value, for which this exception is thrown. This field
 * value can be used while creating the message in an {@link ErrorInfo}.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InvalidValue {
}
