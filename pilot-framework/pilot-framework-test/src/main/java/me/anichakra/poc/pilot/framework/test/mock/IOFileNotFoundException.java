package me.anichakra.poc.pilot.framework.test.mock;

public class IOFileNotFoundException extends RuntimeException {

	public IOFileNotFoundException(String ioFile, Exception e) {
		super("File Not Found:" + ioFile, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
