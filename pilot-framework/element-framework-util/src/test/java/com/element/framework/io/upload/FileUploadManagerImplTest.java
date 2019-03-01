package me.anichakra.poc.pilot.framework.io.upload;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import junit.framework.TestCase;

public class FileUploadManagerImplTest extends TestCase {
	private FileUploadManagerImpl  fileUploadManagerImpl;
	 
	@Mock
	private MessageSource messageSource;
	
	@Mock
	Environment env;	
	@Mock
	MultipartFile multipartFile;
	private static final Logger LOGGER = Logger.getLogger(FileUploadManagerImplTest.class);
	
	protected void setUp() throws Exception {
		super.setUp();
		fileUploadManagerImpl = new FileUploadManagerImpl();
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(fileUploadManagerImpl, "messageSource", messageSource);
		ReflectionTestUtils.setField(fileUploadManagerImpl, "env", env);
	}
	
	@Test
	public void testGettingCellValue_Pass_1() {
		Cell cell=Mockito.mock(Cell.class);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue("1");
		Integer selltype=1;
		String sellVal="1";
		Mockito.when(cell.getCellType()).thenReturn(selltype);
		Mockito.when(cell.getStringCellValue()).thenReturn(sellVal);
		String outputResult =fileUploadManagerImpl.gettingCellValue(cell);
		fileUploadManagerImpl.gettingCellValue(cell);
	}
	
	@Test
	public void testGettingCellValue_Pass_2() {
		Cell cell=Mockito.mock(Cell.class);
		cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
		cell.setCellValue("4");
		Integer selltype=4;
		String sellVal="4";
		Mockito.when(cell.getCellType()).thenReturn(selltype);
		Mockito.when(cell.getStringCellValue()).thenReturn(sellVal);
		String outputResult =fileUploadManagerImpl.gettingCellValue(cell);
		fileUploadManagerImpl.gettingCellValue(cell);
	}
	
	@Test
	public void testGettingCellValue_Pass_3() {
		Cell cell=Mockito.mock(Cell.class);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue("0");
		Integer selltype=0;
		String sellVal="0";
		Mockito.when(cell.getCellType()).thenReturn(selltype);
		Mockito.when(cell.getStringCellValue()).thenReturn(sellVal);
		String outputResult =fileUploadManagerImpl.gettingCellValue(cell);
		fileUploadManagerImpl.gettingCellValue(cell);
	}
	
	@Test
	public void testGettingCellValue_Pass_4() {
		Cell cell=Mockito.mock(Cell.class);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue("0");
		Integer selltype=10;
		String sellVal="0";
		Mockito.when(cell.getCellType()).thenReturn(selltype);
		Mockito.when(cell.getStringCellValue()).thenReturn(sellVal);
		String outputResult =fileUploadManagerImpl.gettingCellValue(cell);
		fileUploadManagerImpl.gettingCellValue(cell);
	}
	
	
	@Test
	public void testGettingCellValue1() {
		String expectedResult = "asd";
		Cell cell=Mockito.mock(Cell.class);
		cell.setCellType( Cell.CELL_TYPE_BOOLEAN);
        cell.setCellValue(true);
        fileUploadManagerImpl.gettingCellValue(cell);
	}
	@Test
	public void testGettingCellValue2() {
		String expectedResult = "asd";
		Cell cell=Mockito.mock(Cell.class);
		cell.setCellType( Cell.CELL_TYPE_NUMERIC);
        fileUploadManagerImpl.gettingCellValue(cell);
	}
	
	@Test
	public void testRetrieveFileContent() {
		try {
			String filePath = "src/test/resources/sampleSpreadsheet.xls";
			fileUploadManagerImpl.retrieveFileContent(filePath, "me.anichakra.poc.pilot.framework.io.upload.Test");
		} catch(Exception ex) {
			LOGGER.error(ex);
		}
	}
	
	@Test(expected=Exception.class)
	public void testRetrieveFileContent_CLassNotFound() {
		try {
			String filePath = "src/test/resources/sampleSpreadsheet.xls";
			fileUploadManagerImpl.retrieveFileContent(filePath, "Test");
		} catch(Exception ex) {
			LOGGER.error(ex);
		}
	}
	
	@Test
	public void testCopyFile() {
		String expectedResult = "asd";
		MultipartFile multipartFile=Mockito.mock(MultipartFile.class);
		Mockito.when(env.getProperty(Mockito.isA(String.class))).thenReturn(expectedResult);

        Mockito.when(multipartFile.getOriginalFilename()).thenReturn(expectedResult);
		String outputResult =fileUploadManagerImpl.copyFile(multipartFile);
		
	}

}
	