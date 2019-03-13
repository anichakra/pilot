package me.anichakra.poc.pilot.framework.test.mock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.assertj.core.util.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

/**
 * This mocks a microservice environment based on JSON based ReST API. Using
 * this one can declaratively compose a test method by mentioning only the API
 * URI, input and expected output.
 * 
 * @author anirbanchakraborty
 *
 */
@Component
@WebAppConfiguration
public class MockApi {

	private final static class URI {
		String uri;
		Object[] uriVariables;

		public URI(String uri, Object[] uriVariables) {
			this.uri = uri;
			this.uriVariables = uriVariables;
		}

		public String getUri() {
			return uri;
		}

		public Object[] getUriVariables() {
			return uriVariables;
		}
	}

	private static final Map<AssertableHttpStatusCode, ResultMatcher> httpStatusResultMap = new HashMap<>();

	private static final Map<HttpMethod, Function<URI, MockHttpServletRequestBuilder>> httpMethodFunctionMap = new HashMap<>();

	static {
		httpStatusResultMap.put(AssertableHttpStatusCode.OK, status().isOk());
		httpStatusResultMap.put(AssertableHttpStatusCode.ACCEPTED, status().isAccepted());
		httpStatusResultMap.put(AssertableHttpStatusCode.CREATED, status().isCreated());
		httpStatusResultMap.put(AssertableHttpStatusCode.UNAUTHORIZED, status().isUnauthorized());
		httpStatusResultMap.put(AssertableHttpStatusCode.FOUND, status().isFound());
		httpStatusResultMap.put(AssertableHttpStatusCode.FORBIDDEN, status().isForbidden());
	}

	static {
//TODO: might need to add more as and when required!
		Function<URI, MockHttpServletRequestBuilder> getFunc = (URI u) -> {
			return u.getUriVariables() != null ? get(u.getUri(), u.getUriVariables()) : get(u.getUri());
		};
		Function<URI, MockHttpServletRequestBuilder> postFunc = (URI u) -> {
			return u.getUriVariables() != null ? post(u.getUri(), u.getUriVariables()) : post(u.getUri());
		};
		Function<URI, MockHttpServletRequestBuilder> putFunc = (URI u) -> {
			return u.getUriVariables() != null ? put(u.getUri(), u.getUriVariables()) : put(u.getUri());
		};
		Function<URI, MockHttpServletRequestBuilder> deleteFunc = (URI u) -> {
			return u.getUriVariables() != null ? delete(u.getUri(), u.getUriVariables()) : delete(u.getUri());
		};
		httpMethodFunctionMap.put(HttpMethod.GET, getFunc);
		httpMethodFunctionMap.put(HttpMethod.POST, postFunc);
		httpMethodFunctionMap.put(HttpMethod.PUT, putFunc);
		httpMethodFunctionMap.put(HttpMethod.DELETE, deleteFunc);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private String loadIoFile(String inputDataFile, String path) {
		System.out.println("ioFile " + inputDataFile);
		File ioDir = new File(path);
		File io = new File(ioDir, inputDataFile + ".json");
		try {
			String json = Files.contentOf(context.getResource("classpath:" + io.getPath()).getFile(), "UTF-8");
			System.out.println("json " + json);
			return json;
		} catch (IOException e) {
			throw new TestDataParsingException(inputDataFile, e);
		}
	}

	private void matchJson(final ResultActions resultActions, String jsonOutput) {
		try {
			resultActions.andExpect(MockMvcResultMatchers.content().json(jsonOutput));
		} catch (Exception e) {
			throw new AssertionException("Cannot perform response match", e);
		}
	}

	private void performAssertion(AssertionData assertionData, ResultActions resultActions) throws Exception {
		Optional.ofNullable(httpStatusResultMap.get(assertionData.getHttpStatusCode())).ifPresent(c -> {
			try {
				resultActions.andExpect(c);
			} catch (Exception e) {
				throw new AssertionException("Cannot perform HTTP Status match", e);
			}
		});
		Optional.ofNullable(assertionData).ifPresent(
				c -> performResultMatch(resultActions, readJSONDataFromFile(c.getJsonOutputFileName(), c.getPath())));

	}

	private void performResultMatch(final ResultActions resultActions, String jsonOutput) {
		Optional.ofNullable(jsonOutput).ifPresent(c -> matchJson(resultActions, c));
	}

	private RequestBuilder populateRequestBuilder(String uri, Object[] uriVariables, HttpMethod httpMethod,
			String jsonInput, Map<String, Object> headers) {
		MockHttpServletRequestBuilder requestBuilder = httpMethodFunctionMap.get(httpMethod)
				.apply(new URI(uri, uriVariables));
		Optional.ofNullable(jsonInput).ifPresent(c -> requestBuilder.content(c).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		Optional.ofNullable(headers)
				.ifPresent(c -> c.entrySet().forEach(e -> requestBuilder.header(e.getKey(), e.getValue())));
		return requestBuilder;
	}

	/**
	 * Reads the test data files from some path
	 * 
	 * @param inputDataFile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	protected String readJSONDataFromFile(String inputDataFile, String path) {
		return Optional.ofNullable(inputDataFile).map(c -> loadIoFile(c, path)).orElse(null);
	}

	/**
	 * Call or invoke the API using the URI and method by sending the input data.
	 * But this does not assert the call.
	 * 
	 * @param uri        The URI of the ReST API
	 * @param httpMethod The ReST Method
	 * @param inputData  This contains the json input and expected output
	 * @return
	 * @throws Exception
	 */
	protected <T> ApiResult<T> call(String uri, HttpMethod httpMethod, InputData inputData) throws Exception {
		String jsonInput = Optional.ofNullable(inputData)
				.map(c -> readJSONDataFromFile(c.getJsonInputFileName(), c.getPath())).orElse(null);
		Map<String, Object> headers = Optional.ofNullable(inputData).map(c -> inputData.getHeaders()).orElse(null);
		RequestBuilder requestBuilder = populateRequestBuilder(uri, inputData.getUriVariables(), httpMethod, jsonInput,
				headers);
		ResultActions resultActions;
		try {
			resultActions = mockMvc.perform(requestBuilder).andDo(print());
		} catch (Exception e) {
			throw new ApiException(e);
		}
		return new ApiResult<>(resultActions);
	}

	/**
	 * 
	 * @param uri           The URI of the ReST API
	 * @param httpMethod    The HTTP Method like, Get, Post, Put, Delete, etc
	 * @param inputData     The input data
	 * @param assertionData The assertion data
	 * @throws Exception
	 */
	protected void assertCall(String uri, HttpMethod httpMethod, InputData inputData, AssertionData assertionData)
			throws Exception {
		ResultActions resultActions = call(uri, httpMethod, inputData).getResultActions();
		performAssertion(assertionData, resultActions);
	}

	/**
	 * Calls a post method
	 * 
	 * @param uri       The post method uri
	 * @param inputData The input data passed as part of the post method
	 * @return
	 * @throws Exception
	 */
	public <T> ApiResult<T> postCall(String uri, InputData inputData) throws Exception {
		return call(uri, HttpMethod.POST, inputData);
	}

	/**
	 * Calls a get method
	 * 
	 * @param uri       The post method uri
	 * @param inputData The input data passed as part of the post method
	 * @return
	 * @throws Exception
	 */
	public <T> ApiResult<T> getCall(String uri, InputData inputData) throws Exception {
		return call(uri, HttpMethod.GET, inputData);
	}

	/**
	 * Calls a put method
	 * 
	 * @param uri       The post method uri
	 * @param inputData The input data passed as part of the post method
	 * @return
	 * @throws Exception
	 */
	public <T> ApiResult<T> putCall(String uri, InputData inputData) throws Exception {
		return call(uri, HttpMethod.PUT, inputData);
	}

	/**
	 * Calls a delete method
	 * 
	 * @param uri       The post method uri
	 * @param inputData The input data passed as part of the post method
	 * @return
	 * @throws Exception
	 */
	public <T> ApiResult<T> deleteCall(String uri, InputData inputData) throws Exception {
		return call(uri, HttpMethod.DELETE, inputData);
	}

	/**
	 * Assets a delete call
	 * 
	 * @param uri           The URI of the ReST API
	 * @param assertionData The assertion data
	 * @throws Exception
	 */
	public void assertDeleteCall(String uri, AssertionData assertionData) throws Exception {
		assertCall(uri, HttpMethod.DELETE, null, assertionData);
	}

	/**
	 * Asserts a delete call with input data
	 * 
	 * @param uri           The uri of the delete method
	 * @param inputData     The input data
	 * @param assertionData The assertion data
	 * @throws Exception
	 */
	public void assertDeleteCall(String uri, InputData inputData, AssertionData assertionData) throws Exception {
		assertCall(uri, HttpMethod.DELETE, inputData, assertionData);
	}

	/**
	 * Asserts a get call with only assertion data
	 * 
	 * @param uri           The uri of the get method call
	 * @param assertionData The assertion data
	 * @throws Exception
	 */
	public void assertGetCall(String uri, AssertionData assertionData) throws Exception {
		assertCall(uri, HttpMethod.GET, null, assertionData);
	}

	/**
	 * Asserts a get call using input data
	 * 
	 * @param uri           The uri of the get method call
	 * @param inputData     The input data
	 * @param assertionData The assertion data
	 * @throws Exception
	 */
	public void assertGetCall(String uri, InputData inputData, AssertionData assertionData) throws Exception {
		assertCall(uri, HttpMethod.GET, inputData, assertionData);
	}

	/**
	 * Asserts a post call using input data and assertion data
	 * 
	 * @param uri           The uri of the post call
	 * @param inputData     The input data
	 * @param assertionData The assertion data
	 * @throws Exception
	 */
	public void assertPostCall(String uri, InputData inputData, AssertionData assertionData) throws Exception {
		assertCall(uri, HttpMethod.POST, inputData, assertionData);
	}

	/**
	 * Asserts a put call using input data and assertion data
	 * 
	 * @param uri           The uri of the put call
	 * @param inputData     The input data
	 * @param assertionData The assertion data
	 * @throws Exception
	 */
	public void assertPutCall(String uri, InputData inputData, AssertionData assertionData) throws Exception {
		assertCall(uri, HttpMethod.PUT, inputData, assertionData);
	}

}
