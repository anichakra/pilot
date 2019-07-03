package me.anichakra.poc.pilot.framework.test.rest.impl;

/**
 * This exception is thrown when there is an exception in either calling the
 * API.
 * 
 * @author anirbanchakraborty
 *
 */
public class RestApiException extends RuntimeException {

	public RestApiException(Exception e) {
		super("Exception in performing ReST call", e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
