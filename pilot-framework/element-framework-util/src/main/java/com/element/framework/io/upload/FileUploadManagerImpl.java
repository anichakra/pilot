package me.anichakra.poc.pilot.framework.io.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import me.anichakra.poc.pilot.framework.annotation.Upload;

 

@Service
public class FileUploadManagerImpl<T> implements FileUploadManager<T>{
	private static final Logger LOGGER = LogManager.getLogger();
	 
	@Autowired
	Environment env;	
	@Autowired
	private MessageSource messageSource;

	 @SuppressWarnings("static-access")
	public static String gettingCellValue(Cell cell) {
		 String value="";

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue().trim();
			break;
			case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
			case Cell.CELL_TYPE_NUMERIC:
			value = String.valueOf(cell.getNumericCellValue()).trim();
			break;
			}
		return value;
	}	 
	@SuppressWarnings("resource")
	public Collection<T> retrieveFileContent(String path,String modelName) {

		List<T> dataList = new ArrayList<T>();
		FileInputStream fis = null;
		try {
			LOGGER.debug("path --- >> " + path);
			fis = new FileInputStream(path);
			LOGGER.debug("FIS --- >> " + fis);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			LOGGER.debug("start rownum excel="+sheet.getFirstRowNum());
			LOGGER.debug("no of rows in excel="+sheet.getLastRowNum());
			LOGGER.debug("path --- >> " + sheet.getFirstRowNum());
			LOGGER.debug("path --- >> " + sheet.getLastRowNum());
			Class<?> c = Class.forName(modelName);
			LOGGER.debug("path --- >> " + c);
			Object obj;

			 Row headerrow =iterator.next();
				while (iterator.hasNext()) {
					LOGGER.debug("while --- >> " + c);
					Row nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					obj = c.newInstance();
					while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					for (Field field : obj.getClass().getDeclaredFields()) {
						if(field.isAnnotationPresent(Upload.class)){
							field.setAccessible(Boolean.TRUE);
								Upload uploadAttribute = field.getAnnotation(Upload.class);
								if(cell!=null && (cell.getColumnIndex()==uploadAttribute.columnSeq())){
								    String cellValue=gettingCellValue(cell);
								    if(uploadAttribute.isMandatory()){
									if(!StringUtils.isEmpty(cellValue)) {
									field.set(obj, cellValue);
										break;
									}else{
										throw new ExcelUploadException(uploadAttribute.columnName()+" mandatory column values can not be empty.");
									}
									}else{
										field.set(obj, cellValue);
									}
								}
						}					
					  }
					}
					if(!dataList.contains(obj)){
						 dataList.add((T) obj);
					}else{
						throw new ExcelUploadException("Excel should have unique data.");
					}
					}	
		} catch (InstantiationException | IllegalAccessException |IOException | ClassNotFoundException e) {
			throw new ExcelUploadException("Error while retrieving File Content.",e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new ExcelUploadException("Error while retrieving File Content.",e);
				}
			}
		}
		return dataList;
	}

	@Override
	public String copyFile(MultipartFile multipart) {
		final String uuid = UUID.randomUUID().toString();
		File convFile = new File(env.getProperty("upload.filepath"), uuid+multipart.getOriginalFilename());
        try {
        	String type = Files.probeContentType(Paths.get(multipart.getOriginalFilename()));
        	multipart.transferTo(convFile);
		} catch (IllegalStateException | IOException e) {
			throw new ExcelUploadException("Error while copying uploaded file.",e);
		}
		return convFile.getAbsolutePath();
	} 

}
