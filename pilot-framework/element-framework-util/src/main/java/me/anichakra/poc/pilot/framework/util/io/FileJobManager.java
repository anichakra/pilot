package me.anichakra.poc.pilot.framework.util.io;

import java.io.OutputStream;

import me.anichakra.poc.pilot.framework.batch.JobManager;

/**
 * A generic file job manager that can read file into an output stream for a job
 * Id
 * 
 * @author Anirban C1
 * @param <T>
 *            The Job input data type
 * @param <S>
 *            The Job output data type
 * 
 */
public interface FileJobManager<T, S> extends JobManager<T, S> {

	/**
	 * The implementation for this need to get the job instance from JobManager
	 * cache and then for the job reads the associated file and streams that to
	 * the given ObjectOutputStream.
	 * 
	 * @param jobId
	 *            The generated job id from the startJob() method of
	 *            {@link #startJob(me.anichakra.poc.pilot.framework.batch.DataProvider, me.anichakra.poc.pilot.framework.batch.DataProcessor, String, Integer)}
	 * 
	 * @param jobName The name of the job
	 * 
	 * @param initiator
	 *            The initiator of the job.
	 * 
	 * @param filePath
	 *            The directory where from the file generated as part of the job
	 *            will be read from.           
	 * @param os
	 *            The ObjectOutputStream when the file would be read from.
	 */
	void readFile(String jobId, String jobName, String initiator, String filePath, OutputStream os,String fileExtension);
}