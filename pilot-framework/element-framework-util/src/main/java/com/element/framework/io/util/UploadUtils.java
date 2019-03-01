package me.anichakra.poc.pilot.framework.io.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadUtils {
	private static final Logger LOGGER = LogManager.getLogger();
	public String validate(MultipartFile file,String uploadFilePath){
		
		try{
			// Creating the directory to store file
			File dir = new File(uploadFilePath+"tmpFiles");
			if (!dir.exists())
				dir.mkdirs();
			// Create the file on server File			
			File serverFile = new File(dir,new File(file.getOriginalFilename()).getName());
			file.transferTo(serverFile);
			Files.probeContentType(Paths.get(serverFile.getAbsolutePath()));
		} catch (IOException e) {
			LOGGER.error(e);
			return e.getMessage();
		} 
		return "";
	}
}