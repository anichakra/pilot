package me.anichakra.poc.pilot.framework.test.mock;

import java.util.HashMap;
import java.util.Map;

/**
 * The TestData contains the name of the json files for input and expected
 * output along with the request headers. This will be send as part of the
 * MockAPI.
 * 
 * @author anirbanchakraborty
 * @see MockApi
 */
public class TestData {
	private String jsonInputFileName;
	private String jsonOutputFileName;
	private Map<String, String> headers;
	private String path = TEST_DATA_CLASSPATH;

	/**
	 * Default test data path
	 */
	public static final String TEST_DATA_CLASSPATH="data";
	/**
	 * Creates a new TestData without any json input or output
	 */
	public TestData() {
		this.headers = new HashMap<>();
	}

	/**
	 * Creates a TestData with a json input file name and json output file name.
	 * The contents of the files should be of proper json structure. The files
	 * should be kept in the "io" package that should be in the classpath.
	 * 
	 * @param jsonInputFileName  The name of the .json file excluding the extension
	 *                           containing the API input json message. This is not
	 *                           a mandatory parameter.
	 * @param jsonOutputFileName The name of the .json file excluding the extension
	 *                           containing the expected json response for test
	 *                           assertion. This is not a mandatory parameter. If
	 *                           not send then response will not be asserted.
	 */
	public TestData(String jsonInputFileName, String jsonOutputFileName) {
		this();
		this.jsonInputFileName = jsonInputFileName;
		this.jsonOutputFileName = jsonOutputFileName;
	}
	
	/**
	 * Creates a TestData with a json input file name and json output file name.
	 * The contents of the files should be of proper json structure. The files
	 * should be kept in the "io" package that should be in the classpath.
	 * 
	 * @param jsonOutputFileName The name of the .json file excluding the extension
	 *                           containing the expected json response for test
	 *                           assertion. This is not a mandatory parameter. If
	 *                           not send then response will not be asserted.
	 */
	public TestData(String jsonOutputFileName) {
		this();
		this.jsonOutputFileName = jsonOutputFileName;
	}

	/**
	 * Get the headers as {@link Map}
	 * 
	 * @return
	 */
	Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Add headers to the HTTP Request.
	 * 
	 * @param header
	 * @param value
	 */
	public void addHeaders(String header, String value) {
		this.headers.put(header, value);
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
	 * 
	 * @return
	 */
	public String getJsonOutputFileName() {
		return jsonOutputFileName;
	}

	/**
	 * 
	 * @param jsonOutputFileName The json file whose content will be matched with
	 *                           the ReST API response for assertion. The file
	 *                           should be kept in the "io" package that should be
	 *                           in the classpath.
	 */
	public void setJsonOutputFileName(String jsonOutputFileName) {
		this.jsonOutputFileName = jsonOutputFileName;
	}

	/**
	 * Set the classpath for test data
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Get the path for test data. 
	 * @return
	 */
	public String getPath() {
		return this.path;
	}
}
