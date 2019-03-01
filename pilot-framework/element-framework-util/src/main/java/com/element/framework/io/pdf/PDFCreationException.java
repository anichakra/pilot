package me.anichakra.poc.pilot.framework.io.pdf;
/**
 * This exception will be thrown if pdf file cannot be created.
 * @author Anirban C1
 *
 */
public class PDFCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public PDFCreationException(String message) {
		super("PDF creation failed:" + message);
	}
	public PDFCreationException(String message, Exception rootCause) {
		super("PDF creation failed:" + message, rootCause);
	}
}
