package me.anichakra.poc.pilot.framework.io;

/**
 * The exception is thrown when reading the test data.
 * 
 * @author anirbanchakraborty
 *
 */
public class JsonFileParsingException extends RuntimeException {

	/**
	 * Creates this exception from the test file and rootcause.
	 * 
	 * @param ioFile
	 * @param e
	 */
	public JsonFileParsingException(String dataFile, Exception e) {
		super("Cannot read or parse file:" + dataFile, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
