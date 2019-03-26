package me.anichakra.poc.pilot.framework.rest.api;

import org.springframework.http.HttpStatus;

public interface RestConsumer {

	String getName();

	/**
	 * This method Add Http Headers
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	RestConsumer addHeader(String name, String value);

	/**
	 * This method sets the Uri Variables
	 * 
	 * @param uriVariables
	 * @return
	 */
	RestConsumer setUriVariables(Object... uriVariables);

	void setAccept(String accept);

	void setContentType(String contentType);

	void setStatusCode(HttpStatus statusCode);

}
