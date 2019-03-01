package me.anichakra.poc.pilot.framework.io.excel;

import java.io.OutputStream;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import me.anichakra.poc.pilot.framework.annotation.Export;


public abstract class DataProcessorTemplate<K>  {

	/**
	 * The header is created from a sample record. It uses {@link Export}
	 * annotation marked on the fields in the class of the input object to
	 * create the header.
	 * 
	 * @param obj A sample object whose attributes marked as {@link Export} will be used to create the header
	 * @param workbook The excel workbook where the header is created
	 * @param sampleDataSheet A sample data sheet
	 * @param rowCount The number of rows for header
	 * @param colCount The number of colums for header
	 */
	 abstract void createHeader(Object obj, HSSFWorkbook workbook, HSSFSheet sampleDataSheet, int rowCount,int colCount);

	/**
	 * 
	 * @param data
	 * @param sampleDataSheet
	 * @param rowCount
	 * @param colCount
	 */
	 abstract void createDataRows(Collection<K> data, HSSFSheet sampleDataSheet, int rowCount, int colCount) ;

	/** Compute table cell value from given data */
	 abstract void createColumnData(Object object, Row dataRow, int colCount) ;

	 abstract  CellStyle setHeaderStyle(HSSFWorkbook sampleWorkBook) ;
	 abstract  CellStyle setRowStyle(HSSFWorkbook sampleWorkBook) ;
	/**
	 * Deletes all temporary files of the HSSFWorkbook instance
	 * 
	 * @param workbook
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	 abstract void deleteSXSSFTempFiles(HSSFWorkbook workbook) throws NoSuchFieldException, IllegalAccessException;

	/** To create the OutputStream for writing excel */
	 abstract OutputStream createOutputStream() ;

}
