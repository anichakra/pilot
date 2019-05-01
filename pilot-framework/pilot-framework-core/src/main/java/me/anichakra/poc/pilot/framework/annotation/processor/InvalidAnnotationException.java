package me.anichakra.poc.pilot.framework.annotation.processor;

import org.springframework.validation.BeanPropertyBindingResult;

/**
 * This exception is thrown when processing the annotations during service bean creation fail.
 * @author anirbanchakraborty
 *
 */
public class InvalidAnnotationException extends RuntimeException {

	/**
	 * 
	 * @param string
	 * @param bean
	 */
	public InvalidAnnotationException(String string, Object bean) {
		super(string + ":"+  bean.getClass().getName());
	}

	public InvalidAnnotationException(Exception e, Object bean) {
        super("Exception resolving annotation of bean" + ":"+  bean.getClass().getName(), e);
    }

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
