package me.anichakra.poc.pilot.framework.test.mock;

/**
 * This exception is thrown when calling asserting
 * @author anirbanchakraborty
 *
 */
public class AssertionException extends RuntimeException {

	/**
	 * 
	 * @param message Assertion message
	 * @param e Root cause
	 */
	public AssertionException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
