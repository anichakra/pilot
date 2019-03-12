package me.anichakra.poc.pilot.framework.test.mock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * This is a wrapper of {@link ResultActions}, and this wraps the response from
 * an API call.
 * 
 * @author anirbanchakraborty
 *
 * @param <T>
 */
public class ApiResult<T> {
	ObjectMapper mapper;

	/**
	 * Creates an ApiResult from ResultActions
	 * 
	 * @param resultActions
	 */
	ApiResult(ResultActions resultActions) {
		this.resultActions = resultActions;
		mapper = new ObjectMapper();
	}

	private ResultActions resultActions;

	/**
	 * 
	 * @return The wrapped ResultsActions
	 */
	ResultActions getResultActions() {
		return resultActions;
	}

	/**
	 * 
	 * @param clazz The class type of the bean resulted as part of the API call.
	 *              This unmarshalls the JSON response.
	 * @return The unmarshalled JSON object as part of the API response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public T getResultBean(Class<T> clazz)
			throws JsonParseException, JsonMappingException, UnsupportedEncodingException, IOException {
		return mapper.readValue(getJsonResponseAsString(), clazz);
	}

	/**
	 * 
	 * @return The JSON response as String as part of the API call response.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public String getJsonResponseAsString() throws UnsupportedEncodingException {
		return resultActions.andReturn().getResponse().getContentAsString();
	}

}
