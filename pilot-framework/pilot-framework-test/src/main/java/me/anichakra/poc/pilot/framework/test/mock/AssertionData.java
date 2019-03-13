package me.anichakra.poc.pilot.framework.test.mock;

/**
 * This contains the expected output of a rest API call that will be asserted
 * during test call. Along with that the HTTP status code returned is also
 * validated. The latter is mandatory or at least need to be asserted.
 * 
 * @author anirbanchakraborty
 *
 */
public class AssertionData {
	private String jsonOutputFileName;
	private AssertableHttpStatusCode httpStatusCode;
	private String path = ASSERTION_DATA_CLASSPATH;
	/**
	 * Default test assertion data classpath
	 */
	public static final String ASSERTION_DATA_CLASSPATH = "data";

	/**
	 * Creates an AssertionData with a valid HTTP Status Code
	 * 
	 * @param httpStatusCode
	 */
	public AssertionData(AssertableHttpStatusCode httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * Creates an AssertionData using HTTP Status Code and a json file name
	 * containing the expected json output for the rest API call to be tested. The
	 * file name should not contain the extension which should be always ".json".
	 * 
	 * @param httpStatusCode
	 * @param jsonOutputFileName
	 */
	public AssertionData(AssertableHttpStatusCode httpStatusCode, String jsonOutputFileName) {
		this.httpStatusCode = httpStatusCode;
		this.jsonOutputFileName = jsonOutputFileName;
	}

	/**
	 * 
	 * @return
	 */
	public AssertableHttpStatusCode getHttpStatusCode() {
		return this.httpStatusCode;
	}

	
	/**
	 * 
	 * @param httpStatusCode
	 */
	public void setHttpStatusCode(AssertableHttpStatusCode httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getJsonOutputFileName() {
		return jsonOutputFileName;
	}

	/**
	 * s
	 * @param jsonOutputFileName
	 */
	public void setJsonOutputFileName(String jsonOutputFileName) {
		this.jsonOutputFileName = jsonOutputFileName;
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
}
