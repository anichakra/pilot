package me.anichakra.poc.pilot.framework.annotation;

public class BeanPostProcessorValidationException extends RuntimeException {

	public BeanPostProcessorValidationException(String string, Object bean) {
		super(string + ":"+  bean.getClass().getName());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
