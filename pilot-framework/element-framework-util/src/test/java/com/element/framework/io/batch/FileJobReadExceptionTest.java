package me.anichakra.poc.pilot.framework.io.batch;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.anichakra.poc.pilot.framework.util.io.FileJobReadException;

public class FileJobReadExceptionTest {
	private  FileJobReadException FileJobReadException; 
	

	@Before
	public void setUp() throws Exception {
		
	}

	@Test/*(expected=Exception.class)*/
	public void testFileJobReadException() {
		String name = "sds";
		Exception e = new Exception();
		FileJobReadException fileJobReadException= new FileJobReadException(name, e) ;
		
	}

}
