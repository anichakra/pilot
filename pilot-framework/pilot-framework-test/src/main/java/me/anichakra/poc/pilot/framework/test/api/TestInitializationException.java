package me.anichakra.poc.pilot.framework.test.api;

public class TestInitializationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestInitializationException(String message, Class<?> clazz, Exception e) {
		super(message + ":" + clazz.getName(), e);
	}

	public TestInitializationException(String message, Class<?> clazz) {
		super(message + ":" + clazz.getName());
	}

}
