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
@Constraint(validatedBy = EitherNotBlank.CheckAtLeastOneNotBlankValidator.class)
@Documented
public @interface EitherNotBlank {
	
	String message() default "EitherNotBlank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fieldNames();

    public static class CheckAtLeastOneNotBlankValidator implements ConstraintValidator<EitherNotBlank, Object> {
    	private String[] fieldNames;

        @Override
    	public void initialize(EitherNotBlank constraintAnnotation) {
            this.fieldNames = constraintAnnotation.fieldNames();
        }

        public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {

            if (object == null)
                return true;

            try {

                for (String fieldName:fieldNames){
                    Object property = PropertyUtils.getProperty(object, fieldName);
                    if(property!=null){
                    	if((property instanceof Number) &&  !((Integer)property ==0)) return true;
                    	if((property instanceof String) && !(property.toString().trim().equals(""))) return true;
                    }
                }
                return false;

            } catch (Exception e) {
                return false;
            }
        }

    }
}	
