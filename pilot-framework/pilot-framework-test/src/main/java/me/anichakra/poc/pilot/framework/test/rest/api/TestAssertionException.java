package me.anichakra.poc.pilot.framework.test.rest.api;

/**
 * This exception is thrown when there is any problem in calling assertion
 * 
 * @see RestApiResult
 * @author anirbanchakraborty
 *
 */
public class TestAssertionException extends RuntimeException {

	/**
	 * 
	 * @param message Assertion message
	 * @param e       Root cause
	 */
	public TestAssertionException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
