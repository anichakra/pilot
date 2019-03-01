package me.anichakra.poc.pilot.framework.io.excel;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

public class ExcelDataProcessorTest {

	private ExcelDataProcessor excelDataProcessor;
	
	private String workSheetName="junit";
	
	private File filePath =new File("junit");

	private String filePattern;
	
	private String jobId;
    @Mock
	private MessageSource messageSource;
	@Mock
	private File filePath1;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		excelDataProcessor = new ExcelDataProcessor(filePath, filePattern, workSheetName, messageSource);
		ReflectionTestUtils.setField(excelDataProcessor, "messageSource", messageSource);
}

	@Test
	public void testCreateHeader() {
		me.anichakra.poc.pilot.framework.io.upload.Test obj = new me.anichakra.poc.pilot.framework.io.upload.Test();
		HSSFWorkbook workbook =  new HSSFWorkbook();
		HSSFSheet sampleDataSheet = workbook.createSheet();
		int rowCount = 1;
		int colCount = 2;
        excelDataProcessor.createHeader(obj, workbook, sampleDataSheet, rowCount, colCount);
	}

	@Test
	public void testCreateDataRows()  {
		
		ArrayList<me.anichakra.poc.pilot.framework.io.upload.Test> data = new ArrayList<>();
		HSSFWorkbook workbook =  new HSSFWorkbook();
		HSSFSheet sampleDataSheet =  workbook.createSheet();
		int rowCount = 1;
		int colCount = 2;
		excelDataProcessor.createDataRows(data, sampleDataSheet, rowCount, colCount);
		}

	
	@Test
	public void test_CreateColumnData_Pass_2() throws NoSuchFieldException, SecurityException {
		Object object = new Object();
		HSSFWorkbook workbook =  new HSSFWorkbook();
		HSSFSheet sampleDataSheet =  workbook.createSheet();
	    excelDataProcessor.concurrentHashMap=null;
		int colCount = 1;
		excelDataProcessor.createColumnData(object, sampleDataSheet.getRow(0), colCount);
	}

	@Test
	public void testSetHeaderStyle() {
		HSSFWorkbook sampleWorkBook = new HSSFWorkbook();
		excelDataProcessor.setHeaderStyle(sampleWorkBook);
	}

	@Test
	public void testSetRowStyle() {
		HSSFWorkbook sampleWorkBook = new HSSFWorkbook();
		excelDataProcessor.setRowStyle(sampleWorkBook);
	}

	//@Test
	public void testDeleteSXSSFTempFiles_Pass_1() throws NoSuchFieldException, IllegalAccessException {
		HSSFWorkbook workbook =  new HSSFWorkbook();
	    workbook.createSheet("_writer");
        workbook.createSheet("_writer1");   
		excelDataProcessor.deleteSXSSFTempFiles(workbook);
		
	}
	
	@Test(expected=Exception.class)
    public void testDeleteSXSSFTempFilesTwo() throws NoSuchFieldException, IllegalAccessException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createSheet("test");
        workbook.createSheet("_writer");        
        excelDataProcessor.deleteSXSSFTempFiles(workbook);
    }

	@Test
	public void test_CreateOutputStream() {
		
		excelDataProcessor.createOutputStream();
	}

	@Test
	public void testPreProcess() {
		String jobId = "123";
		Object obj = new Object();
		excelDataProcessor.preProcess(jobId, obj);
		}

	@Test
	public void testProcess() {
		ArrayList<me.anichakra.poc.pilot.framework.io.upload.Test> data = new ArrayList<>();
		me.anichakra.poc.pilot.framework.io.upload.Test testdata = new me.anichakra.poc.pilot.framework.io.upload.Test();
		testdata.setId("1");
		data.add(testdata);
		 testdata = new me.anichakra.poc.pilot.framework.io.upload.Test();
		 testdata.setId("2");
			data.add(testdata);
			excelDataProcessor.preProcess(jobId, testdata);
			excelDataProcessor.process(data);
	}

	@Test
	public void test_PostProcess_Pass_1() {
		  Object obj = new Object();
		  ArrayList<me.anichakra.poc.pilot.framework.io.upload.Test> data = new ArrayList<>();
          me.anichakra.poc.pilot.framework.io.upload.Test testdata = new me.anichakra.poc.pilot.framework.io.upload.Test();
          testdata.setId("1");
          data.add(testdata);
          testdata = new me.anichakra.poc.pilot.framework.io.upload.Test();
          testdata.setId("2");
          data.add(testdata);
          excelDataProcessor.preProcess(jobId, testdata);
		  excelDataProcessor.postProcess(obj);
		}
	
	@Test(expected=Exception.class)
    public void testPostProcessException() {
        Object obj = new Object();
        excelDataProcessor.postProcess(obj);
        }

	@Test
	public void testGetPrivateAttribute()  throws NoSuchFieldException, IllegalAccessException{
		
		try {
			excelDataProcessor.getPrivateAttribute(new me.anichakra.poc.pilot.framework.io.upload.Test(), "id");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLocalizeColumnName() {
		String name = "junit";
		Mockito.when(excelDataProcessor.localizeColumnName(name)).thenReturn(null);
		excelDataProcessor.localizeColumnName(name);
		}
	
	@Test(expected=Exception.class)
	public void testLocalizeColumnName1() {
	String name = "junit";
		Mockito.when(excelDataProcessor.localizeColumnName("")).thenReturn("");
		excelDataProcessor.localizeColumnName(null);
		}
}
