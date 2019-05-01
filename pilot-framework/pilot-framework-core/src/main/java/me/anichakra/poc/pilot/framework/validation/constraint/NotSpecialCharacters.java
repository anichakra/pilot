/**
 * 
 */
package me.anichakra.poc.pilot.framework.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSpecialCharacters.CheckSpecialCharactersValidator.class)
@Documented
public @interface NotSpecialCharacters {
	
	String message() default "{me.anichakra.poc.pilot.framework.validator.constraints.checkspecialcharacters}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String specialCharsStr();

    public static class CheckSpecialCharactersValidator implements ConstraintValidator<NotSpecialCharacters, Object> {

    	private String specialCharsStr;

        @Override
    	public void initialize(NotSpecialCharacters constraintAnnotation) {
            this.specialCharsStr = constraintAnnotation.specialCharsStr();
        }

        public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {

            if (object == null)
                return true;
            
            try {
            	if(! (object instanceof String)){
            		return true;
            	}
            	
            	char[] specialChars = specialCharsStr.toCharArray();
            	char[] fieldChars = ((String)object).toCharArray();
            	
            	Set<Character> chars = new HashSet<Character>();
            	Set<Character> intersection = new HashSet<Character>();

            	for(char fieldChar:fieldChars){
            		chars.add(fieldChar);
            	}
            	for(char specialChar:specialChars){
            		if(chars.contains(specialChar)){
            			intersection.add(specialChar);
            		};
            	}
            	
            	StringBuilder sb = new StringBuilder(chars.size());
            	
            	
            	Iterator<Character> itemIterator = intersection.iterator();
            	if (itemIterator.hasNext()) {
            		sb.append("\"").append(itemIterator.next()).append("\"");
            	  while (itemIterator.hasNext()) {
            		  sb.append(",\"").append(itemIterator.next()).append("\"");
            	  }
            	}
            	       
            	if(intersection.size() > 0){
            		constraintContext.disableDefaultConstraintViolation();
            		
            		constraintContext.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();
            		return false;
            	}
            	
                return true;

            } catch (Exception e) {
                return false;
            }
        }

    }
}	
