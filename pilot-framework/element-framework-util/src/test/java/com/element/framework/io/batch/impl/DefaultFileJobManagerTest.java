package me.anichakra.poc.pilot.framework.io.batch.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import me.anichakra.poc.pilot.framework.batch.AsyncJobService;
import me.anichakra.poc.pilot.framework.batch.Job;
import me.anichakra.poc.pilot.framework.batch.JobRequest;
import me.anichakra.poc.pilot.framework.batch.JobResponse;
import me.anichakra.poc.pilot.framework.batch.JobResponse.JobStatus;
import me.anichakra.poc.pilot.framework.io.batch.FileJobReadException;
import me.anichakra.poc.pilot.framework.io.excel.ExcelDataProcessor;


public class DefaultFileJobManagerTest {


	private DefaultFileJobManager defaultFileJobManager;
	@Mock 
	private AsyncJobService asyncJobService;
	
	@Before
	public void setUp() throws Exception {
		defaultFileJobManager = new DefaultFileJobManager<>();
		defaultFileJobManager  = Mockito.spy(defaultFileJobManager );
		ReflectionTestUtils.setField(defaultFileJobManager, "asyncJobService", asyncJobService);
		asyncJobService = new AsyncJobService<>();
			
	}

	@Test
	public void testReadFile() {
		String jobId = "1";
		String jobName =  "test";
		String filePath = "";
		String initiator =  "1";
		String fileExtension ="xls";
		defaultFileJobManager = new DefaultFileJobManager(){
		    public  void createFile(File excelFile,OutputStream os,JobResponse jobResponse){
		        
		    }
		};
		OutputStream os =  new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {
								
			}};
			URL main = DefaultFileJobManagerTest.class.getResource("DefaultFileJobManagerTest.class");
			String path = main.getPath().replace("DefaultFileJobManagerTest.class", "");
 		    JobRequest jobRequest = new JobRequest("1", new TestDataProvider(), new ExcelDataProcessor(new File(path), "xls", "junit", null), "1");
		    Job job = new Job("1", jobRequest);
		    defaultFileJobManager.setJobMaptest(job);
		   defaultFileJobManager.readFile(jobId, jobName, path, initiator, os, fileExtension);
	}
	
	@Test(expected=Exception.class)
    public void testReadFileexception() {
        String jobId = "1";
        String jobName =  "test";
        String filePath = "";
        String initiator =  "1";
        String fileExtension ="xls";
        OutputStream os =  new OutputStream() {
            @Override
            public void write(int arg0) throws IOException {
                
            }};
            		
            URL main = DefaultFileJobManagerTest.class.getResource("DefaultFileJobManagerTest.class");
            String path = main.getPath().replace("DefaultFileJobManagerTest.class", "");

            JobRequest jobRequest = new JobRequest("1", new TestDataProvider(), new ExcelDataProcessor(new File(path), "xls", "junit", null), "1");
            Job job = new Job("1", jobRequest);
           defaultFileJobManager.readFile(jobId, jobName, path, initiator, os, fileExtension);
    }
	
	@Test
    public void testCreateFil_Pass_1() {
	    OutputStream os =  new OutputStream() {

            @Override
            public void write(int arg0) throws IOException {
                
            }};
	    JobResponse jobResponse = new JobResponse(new Date(), JobStatus.COMPLETED);
	    defaultFileJobManager.createFile(new File("src/test/resources/sampleSpreadsheet.xls"), os,jobResponse);
	}
	
	@Test(expected=Exception.class)
    public void testCreateFile_Pass_2() {
     
        JobResponse jobResponse = new JobResponse(new Date(), JobStatus.RUNNING);
        defaultFileJobManager.createFile(new File("src/test/resources/sampleSpreadsheet.xls"), null,jobResponse);
    }
	@Test(expected=Exception.class)
    public void testCreateFileRunexceptionFile() {
     
        JobResponse jobResponse = new JobResponse(new Date(), JobStatus.COMPLETED);
        defaultFileJobManager.createFile(null, null,jobResponse);
    }
    
	@Test(expected=Exception.class)
	public void testCreateFile_Pass_3() {
		File excelFile = new File("src/test/resources/EmptyTextFile.TXT");
		OutputStream os =null;
		JobResponse jobResponse = new JobResponse<>(new Date(), JobStatus.COMPLETED);
		   defaultFileJobManager.createFile(excelFile,os, jobResponse);
	}	
	
	@Test(expected=Exception.class)
	public void test_createFile_Pass_4() {
		File excelFile = new File("src/test/resources/EmptyTextFile.TXT");
		OutputStream os =null;
		JobResponse jobResponse = new JobResponse<>(new Date(), JobStatus.COMPLETED);
		   defaultFileJobManager.createFile(excelFile,os, jobResponse);
	}	
	
	@Test(expected=FileJobReadException.class)
    public void testCreateFil_Pass_5() {
	    OutputStream os =  new OutputStream() {

            @Override
            public void write(int arg0) throws IOException {
                
            }};
	    JobResponse jobResponse = new JobResponse(new Date(), JobStatus.COMPLETED);
	    defaultFileJobManager.createFile(null, os,jobResponse);
	}

}
