package me.anichakra.poc.pilot.framework.test.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.anichakra.poc.pilot.framework.io.JsonFileReader;

/**
 * This contains the information about the request body that need to be sent as
 * part of a ReST API call mainly for post and put calls.
 * 
 * @author anirbanchakraborty
 * @see MockRestApi
 */
public class RequestBody {
	private String json;

	/**
	 * Creates a request body using the json input file name containing the input to
	 * the rest API.
	 * 
	 * @param jsonInputFileName Only the file name of the file excluding path and
	 *                          extension
	 */
	public RequestBody(String jsonInputFileName) {
		this.json = JsonFileReader.read(jsonInputFileName);
	}

	/**
	 * Creates a request body using the json input file name containing the input to
	 * the rest API.
	 * 
	 * @param path              The directory path after classpath where the json
	 *                          input file is kept
	 * @param jsonInputFileName Only the file name of the file excluding path and
	 *                          extension
	 */
	public RequestBody(String path, String jsonInputFileName) {
		this.json = JsonFileReader.read(path, jsonInputFileName);
	}

	/**
	 * Creates a request body by marshalling the input object to a json data before
	 * sending the same as part of rest API call.
	 * 
	 * @param inputObject
	 * @throws JsonProcessingException
	 */
	public RequestBody(Object inputObject) throws JsonProcessingException {
		ObjectMapper m = new ObjectMapper();
		json = m.writeValueAsString(inputObject);
	}

	/**
	 * 
	 * @return
	 */
	public String getJson() {
		return json;
	}

}
