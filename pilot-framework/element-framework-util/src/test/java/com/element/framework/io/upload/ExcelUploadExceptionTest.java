package me.anichakra.poc.pilot.framework.io.upload;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ExcelUploadExceptionTest {

	private ExcelUploadException excelUploadException;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExcelUploadExceptionString() {
		String message = "junit";
		ExcelUploadException ExcelUploadException = new ExcelUploadException(message);
		Exception rootCause= new Exception();
		ExcelUploadException ExcelUploadException1= new ExcelUploadException(message, rootCause);
	}

	
}
