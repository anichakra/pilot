package me.anichakra.poc.pilot.framework.io.pdf;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import me.anichakra.poc.pilot.framework.batch.DataProcessor;
import me.anichakra.poc.pilot.framework.batch.DataProvider;

public class PDFJobRequestTest {

	private PDFJobRequest pdfJobRequest;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPDFJobRequestStringDataProviderOfTStringStringStringMessageSource() {
		String jobName = "junit";
		DataProvider dataProvider = new DataProvider() {

			@Override
			public int count() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Collection retrieve(int beginIndex, int chunkSize) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		DataProcessor dataProcessor = new DataProcessor() {

			@Override
			public void preProcess(String processId, Object obj) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Collection process(Collection data) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void postProcess(Object obj) {
				// TODO Auto-generated method stub
				
			}
		};
		String initiator = "junit";
		PDFJobRequest PDFJobRequest = new PDFJobRequest(jobName, dataProvider, dataProcessor, initiator);
		String filePath = "junit";
		String workSheetName = "junit";
		MessageSource messageSource = new MessageSource() {
			
			@Override
			public String getMessage(String code, Object[] args, String defaultMessage,
					Locale locale) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getMessage(String code, Object[] args, Locale locale)
					throws NoSuchMessageException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getMessage(MessageSourceResolvable resolvable, Locale locale)
					throws NoSuchMessageException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		PDFJobRequest PDFJobRequest1 = new PDFJobRequest(jobName, dataProvider, initiator, filePath, workSheetName, messageSource);

	}

/*	@Test
	public void testPDFJobRequestStringDataProviderOfTDataProcessorOfTVoidString() {
		fail("Not yet implemented");
	}

	@Test
	public void testJobRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJobName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDataProvider() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDataProcessor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInitiator() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChunkSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetChunkSize() {
		fail("Not yet implemented");
	}
*/
}
