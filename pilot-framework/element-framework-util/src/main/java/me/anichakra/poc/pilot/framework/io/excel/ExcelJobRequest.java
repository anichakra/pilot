package me.anichakra.poc.pilot.framework.io.excel;

import java.io.File;

import org.springframework.context.MessageSource;

import me.anichakra.poc.pilot.framework.batch.DataProcessor;
import me.anichakra.poc.pilot.framework.batch.DataProvider;
import me.anichakra.poc.pilot.framework.batch.Job;
import me.anichakra.poc.pilot.framework.batch.JobRequest;
import me.anichakra.poc.pilot.framework.io.batch.FileJobManager;

/**
 * This contains additional job request information for {@link FileJobManager}
 * 
 * @author bisenb
 *
 * @param <T>
 * @see Job
 */
public class ExcelJobRequest<T> extends JobRequest<T, Void> {

	
	/**
	 * 
	 * @param jobName
	 * @param dataProvider
	 * @param initiator
	 * @param filePath
	 * @param workSheetName
	 */
	@SuppressWarnings("unchecked")
	public ExcelJobRequest(String jobName, 
			DataProvider<T> dataProvider, 
			String initiator, 
			String filePath,
			String workSheetName,
			MessageSource messageSource) {
	super(jobName, dataProvider, (DataProcessor<T, Void>) new ExcelDataProcessor<>(new File(filePath),jobName,workSheetName,messageSource), initiator);
	}

	/**
	 * 
	 * @param jobName
	 * @param dataProvider
	 * @param dataProcessor
	 * @param initiator
	 */
	public ExcelJobRequest(String jobName, DataProvider<T> dataProvider, DataProcessor<T, Void> dataProcessor,
			String initiator) {
		super(jobName, dataProvider, dataProcessor, initiator);
	}

}
