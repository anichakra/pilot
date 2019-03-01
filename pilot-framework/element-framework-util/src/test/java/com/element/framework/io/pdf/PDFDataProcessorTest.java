package me.anichakra.poc.pilot.framework.io.pdf;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

public class PDFDataProcessorTest {

	private PDFDataProcessor pdfDataProcessor;

	@Mock 
	private MessageSource messageSource;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		pdfDataProcessor= new PDFDataProcessor(new File(""), "xls", "test", messageSource) {
		    public OutputStream createOutputStream() {
		    return null;
			}}; 
		ReflectionTestUtils.setField(pdfDataProcessor, "messageSource", messageSource);
	}

	@Test
	public void test() {

		TestPdfData test = new TestPdfData();
		test.setId("1");
		test.setName("smith");
		Mockito.when(messageSource.getMessage("name", null, Locale.US)).thenReturn(null);
		pdfDataProcessor.preProcess("1",test);
	}
	
	@Test
	public void testProcess() {
		ArrayList<TestPdfData> testPdfData = new ArrayList();
		TestPdfData test = new TestPdfData();
		test.setId("1");
		test.setName("smith");
		testPdfData.add(test);
		test = new TestPdfData();
		test.setId("2");
		test.setName("smith");
		testPdfData.add(test);
		test = new TestPdfData();
		test.setId("3");
		test.setName("smith");
		testPdfData.add(test);
        Mockito.when(messageSource.getMessage("name", null, Locale.US)).thenReturn(null);
		pdfDataProcessor.process(testPdfData);
	}


	@Test(expected=Exception.class)
	public void testPostProcess() {
		ArrayList<TestPdfData> testPdfData = new ArrayList();
		TestPdfData test = new TestPdfData();
		test.setId("1");
		test.setName("smith");
		testPdfData.add(test);
		Mockito.when(messageSource.getMessage("name", null, Locale.US)).thenReturn(null);
		pdfDataProcessor.preProcess("1",test);
		pdfDataProcessor.postProcess(test);
	}
	@Test(expected=Exception.class)
    public void testPostProcesException() {
        pdfDataProcessor.postProcess(null);
    }

	@Test
	public void testLocalizeColumnName() {
	    String name = "";
		pdfDataProcessor.localizeColumnName("");
	}

	@Test
	public void testCreateColumnData() {
		TestPdfData test = new TestPdfData();
		test.setId("1");
		test.setName("smith");
		Mockito.when(messageSource.getMessage("name", null, Locale.US)).thenReturn(null);
		pdfDataProcessor.preProcess("1",test);
		me.anichakra.poc.pilot.framework.io.upload.Test object = new me.anichakra.poc.pilot.framework.io.upload.Test();
		object.setId("1");
		pdfDataProcessor.createColumnData(object);
	}

	@Test
	public void testCreateHeader() {
		TestPdfData test = new TestPdfData();
		test.setId("1");
		test.setName("smith");
		Mockito.when(messageSource.getMessage("name", null, Locale.US)).thenReturn(null);
		pdfDataProcessor.preProcess("1",test);
		me.anichakra.poc.pilot.framework.io.upload.Test object = new me.anichakra.poc.pilot.framework.io.upload.Test();
		object.setId("1");
		pdfDataProcessor.createHeader(object);
	}
	
	@Test
	public void testCreateFile() {	
		File file = new File("src/test/resources/sampleSpreadsheet.xls");
		pdfDataProcessor.createFile(file);
	} 
	
	
	
}
