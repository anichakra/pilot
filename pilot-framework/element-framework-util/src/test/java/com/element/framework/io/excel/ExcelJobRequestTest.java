package me.anichakra.poc.pilot.framework.io.excel;

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

public class ExcelJobRequestTest {
	
	private  ExcelJobRequest  excelJobRequest;

	@Before
	public void setUp() throws Exception {
	}

	@Test/*(expected=Exception.class)*/
	public void testExcelJobRequestStringDataProviderOfTStringStringStringMessageSource() {
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
		ExcelJobRequest ExcelJobRequest= new ExcelJobRequest(jobName, dataProvider, dataProcessor, initiator);
	}

	@Test
	public void testExcelJobRequestStringDataProviderOfTDataProcessorOfTVoidString() {
		
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
	
		String initiator = "junit";
		String workSheetName = "junit";
		String filePath = "junit";
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
		ExcelJobRequest ExcelJobRequest= new ExcelJobRequest(jobName, dataProvider, initiator, filePath, workSheetName, messageSource);

	}

}
