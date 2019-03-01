package me.anichakra.poc.pilot.framework.util.io;

/**
 * This exception is thrown when there is some problem in reading a file
 * @author Anirban C1
 *
 */
public class FileJobReadException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a FileJobReadException with a string message and exception
	 * @param message
	 * @param e
	 */
	public FileJobReadException(String message, Exception e) {
		super("File reading failed: " + message, e);
	}
}
