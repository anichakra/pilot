package me.anichakra.poc.pilot.framework.io;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Simple Json file reader, that reads a file whose content is json and
 * extension is .json and returns the content into JSON as String.
 * 
 * @author anirbanchakraborty
 *
 */
public class JsonFileReader {

	/**
	 * Extension of the .json file
	 */
	private static final String FILE_EXTENSION = ".json";
	/**
	 * Default test input data classpath
	 */
	public static final String INPUT_DATA_CLASSPATH = "data";

	/**
	 * Reads the json file from classpath. Validates the file and the structure of
	 * the json. Throws TestDataParsingException if either is invalid.
	 * 
	 * @param jsonFileName Name of the file without path or extension. File should
	 *                     be in the default path {@link INPUT_DATA_CLASSPATH} which
	 *                     should be in classpath.
	 * @return The JSON as String
	 */
	public static String read(String jsonFileName) {
		return read(INPUT_DATA_CLASSPATH, jsonFileName);
	}

	/**
	 * Reads the test data files from some path. Validates the file and the
	 * structure of the json. Throws TestDataParsingException if either is invalid.
	 * 
	 * @param jsonFileName Name of the file without path or extension (.json)
	 * @param path         The path where the file is kept. The path root should be
	 *                     in classpath
	 * @return
	 */
	public static String read(String path, String jsonFileName) {
		return Optional.ofNullable(jsonFileName).map(c -> {
			try {
				return loadIoFile(path, c);
			} catch (URISyntaxException | IOException e) {
				throw new JsonFileParsingException(jsonFileName, e);
			}
		}).orElse(null);
	}

	private static String loadIoFile(String path, String inputDataFile) throws URISyntaxException, IOException {
		File ioDir = new File(path);
		File io = new File(ioDir, inputDataFile + FILE_EXTENSION);
		File file = ResourceUtils.getFile("classpath:" + io.getPath());
		String json = new String(Files.readAllBytes(file.toPath()));
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
			mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
			mapper.readTree(json);
		} catch (JsonProcessingException e) {
			throw new JsonFileParsingException(io.getName(), e);
		}
		return json;
	}

}
