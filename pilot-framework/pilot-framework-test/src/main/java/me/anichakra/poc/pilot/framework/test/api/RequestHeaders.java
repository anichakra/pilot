package me.anichakra.poc.pilot.framework.test.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This contains the request headers that need to be passed as part of a ReST
 * API call.
 * 
 * @author anirbanchakraborty
 * @see MockApi
 */
public class RequestHeaders {

	private Map<String, Object> headers;

	/**
	 * Creates an instance of RequestHeaders with one header only.
	 * 
	 * @param name
	 * @param value
	 */
	public RequestHeaders(String name, Object value) {
		this();
		this.headers.put(name, value);
	}

	/**
	 * Creates a blank request header. Use addHeader() to add headers to this
	 * instance.
	 */
	public RequestHeaders() {
		this.headers = new HashMap<>();
	}

	/**
	 * Add a header to the HTTP Query.
	 * 
	 * @param header
	 * @param value
	 * @return This instance
	 */
	public RequestHeaders addHeaders(String header, Object value) {
		this.headers = Optional.ofNullable(this.headers).orElse(new HashMap<>());
		this.headers.put(header, value);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	Map<String, Object> getHeaders() {
		return this.headers;
	}

}
