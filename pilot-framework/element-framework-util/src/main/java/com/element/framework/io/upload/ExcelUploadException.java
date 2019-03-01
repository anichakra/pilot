package me.anichakra.poc.pilot.framework.io.upload;
/**
 * This exception will be thrown if excel file cannot be created.
 * @author Anirban C1
 *
 */
public class ExcelUploadException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public ExcelUploadException(String message) {
		super("Excel upload failed:" + message);
	}
	public ExcelUploadException(String message, Exception rootCause) {
		super("Excel upload failed:" + message, rootCause);
	}
}
