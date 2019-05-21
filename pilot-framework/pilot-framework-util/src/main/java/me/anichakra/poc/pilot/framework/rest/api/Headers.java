package me.anichakra.poc.pilot.framework.rest.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Wrapping the http headers
 * @author anirbanchakraborty
 *
 */
public class Headers {

	private Map<String, String> headerMap = new HashMap<>(5);
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Headers addHeader(String name, String value) {
		this.headerMap.put(name, value);
		return this;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return headerMap.get(name);
	}
	
	public Set<String> getHeaderNames() {
		return headerMap.keySet();
	}
}
