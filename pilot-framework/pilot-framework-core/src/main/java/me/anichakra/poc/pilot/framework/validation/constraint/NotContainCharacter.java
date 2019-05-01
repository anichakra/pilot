/**
 * 
 */
package me.anichakra.poc.pilot.framework.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.beanutils.PropertyUtils;


/**
 * 
 * @author anirbanchakraborty
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotContainCharacter.ValidateCarotFieldValidator.class)
@Documented
public @interface NotContainCharacter {

    String message() default "NotContainCharacter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fieldNames();

    public static class ValidateCarotFieldValidator
            implements ConstraintValidator<NotContainCharacter, Object> {

        private String[] fieldNames;

        @Override
        public void initialize(NotContainCharacter constraintAnnotation) {
            this.fieldNames = constraintAnnotation.fieldNames();
        }

        @Override
        public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
            try {
                for (String fieldName : fieldNames) {
                    Object property = PropertyUtils.getProperty(object, fieldName);
                    if (property != null) {
                        if (property.toString().contains("^"))
                            return false;
                    }
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    }
}