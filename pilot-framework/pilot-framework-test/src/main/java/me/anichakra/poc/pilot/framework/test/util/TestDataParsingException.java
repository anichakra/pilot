package me.anichakra.poc.pilot.framework.test.util;

/**
 * The exception is thrown when reading the test data.
 * 
 * @author anirbanchakraborty
 *
 */
public class TestDataParsingException extends RuntimeException {

	/**
	 * Creates this exception from the test file and rootcause.
	 * 
	 * @param ioFile
	 * @param e
	 */
	public TestDataParsingException(String testDataFile, Exception e) {
		super("Cannot read or parse file:" + testDataFile, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
