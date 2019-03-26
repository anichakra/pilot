package me.anichakra.poc.pilot.framework.rest.impl;

public class InvalidResponseTypeException extends RuntimeException {

	public InvalidResponseTypeException(String responseType) {
		super(responseType);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
