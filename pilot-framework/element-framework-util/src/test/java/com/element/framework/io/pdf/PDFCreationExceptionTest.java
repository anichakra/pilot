package me.anichakra.poc.pilot.framework.io.pdf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PDFCreationExceptionTest {

	private  PDFCreationException PDFCreationException;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPDFCreationExceptionString() {
		String name = "junit";
		PDFCreationException PDFCreationException = new PDFCreationException(name)	;
		String message = "junit";
		Exception rootCause = new Exception();
		PDFCreationException PDFCreationException1 = new PDFCreationException(message, rootCause);	
	}

	
}
