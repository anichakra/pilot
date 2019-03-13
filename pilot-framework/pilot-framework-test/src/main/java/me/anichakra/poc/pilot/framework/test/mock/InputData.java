package me.anichakra.poc.pilot.framework.test.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The InputData contains the name of the json file where the input data is kept
 * and request headers to make an API call.
 * 
 * @author anirbanchakraborty
 * @see MockApi
 */
public class InputData {
	private String jsonInputFileName;
	private Map<String, Object> headers;
	private Object[] uriVariables;
	private String path = INPUT_DATA_CLASSPATH;

	/**
	 * Default test input data classpath
	 */
	public static final String INPUT_DATA_CLASSPATH = "data";

	/**
	 * Creates a InputData using the json input file name containing the input to
	 * the rest API.
	 * 
	 * @param jsonInputFileName
	 */
	public InputData(String jsonInputFileName) {
		this.jsonInputFileName = jsonInputFileName;
	}

	/**
	 * Create a InputData using multiple uri variables. Use the variables in uri
	 * using {}. E.g.
	 * 
	 * <pre>
	 * <code>
	 * 		mockApi.assertGetCall("/vehicle/search?manufacturer={manufacturer}", new InputData("Nissan"));
	 * </code>
	 * </pre>
	 * 
	 * @param uriVariables
	 */
	public InputData(Object... uriVariables) {
		this.uriVariables = uriVariables;
	}

	/**
	 * Get the headers as {@link Map}
	 * 
	 * @return
	 */
	Map<String, Object> getHeaders() {
		return headers;
	}

	/**
	 * Add a header to the HTTP Query.
	 * 
	 * @param header
	 * @param value
	 * @return This instance
	 */
	public InputData addHeaders(String header, Object value) {
		this.headers = Optional.ofNullable(this.headers).orElse(new HashMap<>());
		this.headers.put(header, value);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getJsonInputFileName() {
		return jsonInputFileName;
	}

	/**
	 * 
	 * @param jsonInputFileName The json file whose content will be sent to the ReST
	 *                          API under test. The file should be kept in the "io"
	 *                          package that should be in the classpath.
	 */
	public void setJsonInputFileName(String jsonInputFileName) {
		this.jsonInputFileName = jsonInputFileName;
	}

	/**
	 * Set the classpath for json data file.
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Get the path for json data file.
	 * 
	 * @return
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 
	 * @return
	 */
	Object[] getUriVariables() {
		return uriVariables;
	}

	/**
	 * Sets the uri variables to this instance.
	 * @param uriVariables
	 * @return
	 */
	public InputData setUriVariables(Object... uriVariables) {
		this.uriVariables = uriVariables;
		return this;
	}

}
