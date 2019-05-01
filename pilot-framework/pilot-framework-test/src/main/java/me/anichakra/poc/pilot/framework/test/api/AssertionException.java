package me.anichakra.poc.pilot.framework.test.api;

/**
 * This exception is thrown when there is any problem in calling assertion
 * 
 * @see ApiResult
 * @author anirbanchakraborty
 *
 */
public class AssertionException extends RuntimeException {

	/**
	 * 
	 * @param message Assertion message
	 * @param e       Root cause
	 */
	public AssertionException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
