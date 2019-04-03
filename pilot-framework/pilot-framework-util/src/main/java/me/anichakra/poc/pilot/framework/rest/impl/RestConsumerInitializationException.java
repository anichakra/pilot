package me.anichakra.poc.pilot.framework.rest.impl;

import java.security.GeneralSecurityException;

public class RestConsumerInitializationException extends RuntimeException {

	public RestConsumerInitializationException(String message, GeneralSecurityException e) {
		super(message, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
