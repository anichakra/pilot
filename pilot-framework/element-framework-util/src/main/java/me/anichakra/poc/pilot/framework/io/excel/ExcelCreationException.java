package me.anichakra.poc.pilot.framework.io.excel;
/**
 * This exception will be thrown if excel file cannot be created.
 * @author Anirban C1
 *
 */
public class ExcelCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public ExcelCreationException(String message) {
		super("Excel creation failed:" + message);
	}
	public ExcelCreationException(String message, Exception rootCause) {
		super("Excel creation failed:" + message, rootCause);
	}
}
