package me.anichakra.poc.pilot.framework.annotation.processor;

/**
 * This exception is thrown when processing the annotations during service bean creation fail.
 * @author anirbanchakraborty
 *
 */
public class InvalidAnnotationException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
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



}
