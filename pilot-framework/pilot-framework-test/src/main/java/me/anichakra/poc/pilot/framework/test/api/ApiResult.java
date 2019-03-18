package me.anichakra.poc.pilot.framework.test.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.anichakra.poc.pilot.framework.test.util.JsonFileReader;

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

	public void assertResult(AssertableHttpStatusCode httpStatusCode, String assertionJsonFileName) throws Exception {
		String json = Optional.ofNullable(assertionJsonFileName).map(JsonFileReader::read).orElse(null);
		performAssertion(json, httpStatusCode, resultActions);
	}

	public void assertResult(AssertableHttpStatusCode httpStatusCode, T assertionObject) throws Exception {
		ObjectMapper m = new ObjectMapper();
		String json = m.writeValueAsString(assertionObject);
		performAssertion(json, httpStatusCode, resultActions);

	}

	public void assertResult(AssertableHttpStatusCode httpStatusCode) throws Exception {
		performAssertion(null, httpStatusCode, resultActions);
	}

	private static final Map<AssertableHttpStatusCode, ResultMatcher> httpStatusResultMap = new HashMap<>();

	static {
		httpStatusResultMap.put(AssertableHttpStatusCode.OK, status().isOk());
		httpStatusResultMap.put(AssertableHttpStatusCode.ACCEPTED, status().isAccepted());
		httpStatusResultMap.put(AssertableHttpStatusCode.CREATED, status().isCreated());
		httpStatusResultMap.put(AssertableHttpStatusCode.UNAUTHORIZED, status().isUnauthorized());
		httpStatusResultMap.put(AssertableHttpStatusCode.FOUND, status().isFound());
		httpStatusResultMap.put(AssertableHttpStatusCode.FORBIDDEN, status().isForbidden());
	}

	private void matchJson(final ResultActions resultActions, String jsonOutput) {
		try {
			resultActions.andExpect(MockMvcResultMatchers.content().json(jsonOutput));
		} catch (Exception e) {
			throw new AssertionException("Cannot perform response match", e);
		}
	}

	private void performAssertion(String json, AssertableHttpStatusCode httpStatusCode, ResultActions resultActions)
			throws Exception {
		Optional.ofNullable(httpStatusResultMap.get(httpStatusCode)).ifPresent(c -> {
			try {
				resultActions.andExpect(c);
			} catch (Exception e) {
				throw new AssertionException("Cannot perform HTTP Status match", e);
			}
		});
		Optional.ofNullable(json).ifPresent(c -> performResultMatch(resultActions, c));

	}

	private void performResultMatch(final ResultActions resultActions, String jsonAssertion) {
		Optional.ofNullable(jsonAssertion).ifPresent(c -> matchJson(resultActions, c));
	}

}
