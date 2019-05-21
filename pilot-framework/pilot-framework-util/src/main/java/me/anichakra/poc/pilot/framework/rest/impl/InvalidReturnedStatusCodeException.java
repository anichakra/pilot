package me.anichakra.poc.pilot.framework.rest.impl;


/**
 * 
 * @author anirbanchakraborty
 *
 */
public class InvalidReturnedStatusCodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidReturnedStatusCodeException(String uri, int httpStatusCode) {
		super("Returned Status Code (" + httpStatusCode + ") for ReST API call:"+uri +" is invalid!");
	}

}
