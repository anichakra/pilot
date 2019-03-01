package me.anichakra.poc.pilot.framework.io.excel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ExcelCreationExceptionTest {
 
	private ExcelCreationException excelCreationException;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExcelCreationExceptionString() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	
	}

	@Test
	public void testExcelCreationExceptionStringException() {
		String message = "junit";
		Exception rootCause = new Exception();
		ExcelCreationException ExcelCreationException= new ExcelCreationException(message, rootCause);
	}

	/*@Test
	public void testRuntimeException() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testRuntimeExceptionString() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testRuntimeExceptionStringThrowable() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testRuntimeExceptionThrowable() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testRuntimeExceptionStringThrowableBooleanBoolean() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testException() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testExceptionString() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testExceptionStringThrowable() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testExceptionThrowable() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testExceptionStringThrowableBooleanBoolean() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testToString() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testThrowable() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testThrowableString() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testThrowableStringThrowable() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testThrowableThrowable() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testThrowableStringThrowableBooleanBoolean() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testGetMessage() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testGetLocalizedMessage() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testGetCause() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testInitCause() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testPrintStackTrace() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testPrintStackTracePrintStream() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testPrintStackTracePrintWriter() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testFillInStackTrace() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testGetStackTrace() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testSetStackTrace() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testGetStackTraceDepth() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testGetStackTraceElement() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testAddSuppressed() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}

	@Test
	public void testGetSuppressed() {
		String name = "sd";
		ExcelCreationException ExcelCreationException= new ExcelCreationException(name);
	}
*/
}
