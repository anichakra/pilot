package me.anichakra.poc.pilot.framework.test.mock;

/**
 * This exception is thrown when there is an exception in either calling the API or during assertion.
 * @author anirbanchakraborty
 *
 */
public class ApiException extends RuntimeException {

	public ApiException(Exception e) {
		super("Exception in performing ReST call", e);	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
