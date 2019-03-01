package me.anichakra.poc.pilot.framework.util.io.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import me.anichakra.poc.pilot.framework.batch.Job;
import me.anichakra.poc.pilot.framework.batch.JobManager;
import me.anichakra.poc.pilot.framework.batch.JobResponse;
import me.anichakra.poc.pilot.framework.batch.exception.JobNotCompletedException;
import me.anichakra.poc.pilot.framework.batch.exception.JobNotFoundException;
import me.anichakra.poc.pilot.framework.batch.impl.DefaultJobManager;
import me.anichakra.poc.pilot.framework.io.batch.FileJobManager;
import me.anichakra.poc.pilot.framework.io.batch.FileJobReadException;

/**
 * The default implementation of FileJobManager which retrieves the {@link Job}
 * from the {@link JobManager} cache and reads the generated file as part of the
 * job. The file is created using the pattern
 * <code>jobId + "_" + filePattern</code> from the file path
 * <code>filePath</code>. The file is read using a buffer of 1kb. If job is not
 * completed and this request is made then {@link JobNotCompletedException} is
 * thrown.
 * 
 * @author Anirban C1
 *
 */
@Service("defaultFileJobManager")
public class DefaultFileJobManager<T, S> extends DefaultJobManager<T, S> implements FileJobManager<T, S> {
	public String fileExtension = "xlsx";
	/**
	 * The {@link Job} instance is retrieved from the managed list of jobs using
	 * job id and then validates with initiator. Then the generated file from
	 * batch job is read and passed to output stream so that a continuous read
	 * can be done.
	 */
	@Override
	public void readFile(String jobId, String jobName, String filePath, String initiator, OutputStream os,String fileExtension) {
		this.fileExtension=fileExtension;
		Job<T, S> job = null;
		JobResponse<S> jobResponse = null;
		if (jobMap.containsKey(jobId)) {
			job = jobMap.get(jobId);
			jobResponse = job.getJobResponse(initiator);
		} else { 
			throw new JobNotFoundException(jobId);
		}

		File excelFile = new File(filePath, jobId + "_" + jobName+"."+fileExtension);
		createFile(excelFile,os,jobResponse);
	}
	
	public void createFile(File excelFile,OutputStream os,JobResponse<S> jobResponse){ 
	    if (jobResponse.getStatus() == JobResponse.JobStatus.COMPLETED) {
	        try (FileInputStream inputStream = new FileInputStream(excelFile);
	                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	                DataInputStream in = new DataInputStream(inputStream);) {
	            int length = 0;
	            byte[] bbuf = new byte[1024];

	            while ((in != null) && ((length = in.read(bbuf, 0, bbuf.length)) != -1)) {
	                buffer.write(bbuf, 0, length);
	            }
	            os.write(buffer.toByteArray());
	            buffer.flush();

	        } catch (Exception ex) {
	            throw new FileJobReadException("ERROR_JOB_FILEREAD", ex);
	        }
        } else {
            throw new JobNotCompletedException("ERROR_JOB_FILEREAD");
        }
        
    
	    
	}
	
	public void setJobMaptest(Job job){
		job.execute();
		jobMap.put(job.getId(), job);
		
		
	}

}
