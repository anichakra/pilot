package me.anichakra.poc.pilot.framework.io.upload;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 TODO add documentation
 */
public interface FileUploadManager<T> {

	Collection<T> retrieveFileContent(String path,String modelName);
	String copyFile(MultipartFile file);

}
