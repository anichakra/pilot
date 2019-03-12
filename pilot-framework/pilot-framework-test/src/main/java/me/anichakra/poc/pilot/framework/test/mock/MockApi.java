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
import org.springframework.http.HttpStatus;
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
 * This mocks a microservice environment based on ReST API. Using this one can
 * declaratively compose a test method by mentioning only the API URI, input and
 * expected JSON.
 * <p>
 * Examples of how to write the test cases:
 * <p>
 * 
 * <pre>
 * <code>
 * &#64;MicroserviceTest
&#64;RunWith(MicroserviceTestRunner.class)
&#64;FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleServiceApplicationTest {

	&#64;Autowired
	private MockApi mockApi;

	&#64;Test
		public void a_saveAll() throws Exception {
			mockApi.assertCall("/vehicle/save", HttpMethod.POST, HttpStatus.CREATED,
					new TestData("saveAll_in", "saveAll_out"));
		}


	&#64;Test
	public void b_deleteVehicle() throws Exception {
		Vehicle v = mockApi.<Vehicle>call("/vehicle", HttpMethod.POST, new TestData("save_in", null))
				.getResultBean(Vehicle.class);
		mockApi.assertCall("/vehicle?id=" + v.getId(), HttpMethod.DELETE, HttpStatus.NO_CONTENT);
	}
	
	&#64;Test
	public void c_retrieve() throws Exception {
		mockApi.assertCall("/vehicle/1", HttpMethod.GET, HttpStatus.OK, new TestData("retrieve_out"));
	}

	&#64;Test()
	public void d_searchVehicle() throws Exception {
		mockApi.assertCall("/vehicle/search?manufacturer=Nissan", HttpMethod.GET, HttpStatus.OK,
				new TestData("searchVehicle_out"));
	}

	&#64;Test
	public void e_getPreference() throws Exception {
		mockApi.assertCall("/vehicle/preference", HttpMethod.POST, HttpStatus.OK,
				new TestData("getPreference_in", "getPreference_out"));
	}

	&#64;Test
	public void f_save() throws Exception {
		mockApi.assertCall("/vehicle", HttpMethod.POST, HttpStatus.CREATED, new TestData("save_in", "save_out"));
	}
</code>
 * </pre>
 * 
 * @author anirbanchakraborty
 *
 */
@Component
@WebAppConfiguration
public class MockApi {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private static Map<HttpStatus, ResultMatcher> resultActionStatusMap = new HashMap<HttpStatus, ResultMatcher>();

	private static final Map<HttpMethod, Function<String, MockHttpServletRequestBuilder>> map = new HashMap<>();

	static {

		resultActionStatusMap.put(HttpStatus.OK, status().isOk());
		resultActionStatusMap.put(HttpStatus.ACCEPTED, status().isAccepted());
		resultActionStatusMap.put(HttpStatus.CREATED, status().isCreated());
		resultActionStatusMap.put(HttpStatus.UNAUTHORIZED, status().isUnauthorized());
		resultActionStatusMap.put(HttpStatus.FOUND, status().isFound());
		resultActionStatusMap.put(HttpStatus.FORBIDDEN, status().isForbidden());

		Function<String, MockHttpServletRequestBuilder> getFunc = (String uri) -> {
			return get(uri);
		};
		Function<String, MockHttpServletRequestBuilder> postFunc = (String uri) -> {
			return post(uri);
		};
		Function<String, MockHttpServletRequestBuilder> putFunc = (String uri) -> {
			return put(uri);
		};
		Function<String, MockHttpServletRequestBuilder> deleteFunc = (String uri) -> {
			return delete(uri);
		};
		map.put(HttpMethod.GET, getFunc);
		map.put(HttpMethod.POST, postFunc);
		map.put(HttpMethod.PUT, putFunc);
		map.put(HttpMethod.DELETE, deleteFunc);
	}

	/**
	 * Asserts a ReST API call using the URI
	 * 
	 * @param uri        The URI of the ReST API
	 * @param httpMethod The ReST Method
	 * @param httpStatus The response HTTP Status to assert with
	 * @throws Exception Any exception thrown
	 */
	public void assertCall(String uri, HttpMethod httpMethod, HttpStatus httpStatus) throws Exception {
		assertCall(uri, httpMethod, httpStatus, null);
	}

	/**
	 * Asserts a ReST API call using the URI and sending necessary inputs and test
	 * data.
	 * 
	 * @param uri        The URI of the ReST API
	 * @param httpMethod The ReST Method
	 * @param httpStatus The response HTTP Status to assert with
	 * @param testData   This containing the json input and expected output
	 *                   information
	 * @throws Exception Any exception thrown
	 */
	public void assertCall(String uri, HttpMethod httpMethod, HttpStatus httpStatus, TestData testData)
			throws Exception {
		ResultActions resultActions = call(uri, httpMethod, Optional.ofNullable(testData).orElse(new TestData()))
				.getResultActions();
		performResultActionStatus(httpStatus, resultActions);

		Optional.ofNullable(testData).ifPresent(
				c -> performResultMatch(resultActions, readJSONDataFromFile(c.getJsonOutputFileName(), c.getPath())));

	}

	/**
	 * Call or invoke the API using the URI and method by sending the test data. But
	 * this does not assert the call.
	 * 
	 * @param uri        The URI of the ReST API
	 * @param httpMethod The ReST Method
	 * @param testData   This contains the json input and expected output
	 * @return
	 * @throws Exception
	 */
	public <T> ApiResult<T> call(String uri, HttpMethod httpMethod, TestData testData) {
		RequestBuilder requestBuilder = populateRequestBuilder(uri, httpMethod,
				readJSONDataFromFile(testData.getJsonInputFileName(), testData.getPath()), testData.getHeaders());
		ResultActions resultActions;
		try {
			resultActions = mockMvc.perform(requestBuilder).andDo(print());
		} catch (Exception e) {
			throw new ApiException(e);
		}
		return new ApiResult<>(resultActions);
	}

	/**
	 * Reads the test data files from some path
	 * 
	 * @param testDataFile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	protected String readJSONDataFromFile(String testDataFile, String path) {
		return Optional.ofNullable(testDataFile).map(c -> loadIoFile(c, path)).orElse(null);
	}

	private String loadIoFile(String testDataFile, String path) {
		System.out.println("ioFile " + testDataFile);
		File ioDir = new File(path);
		File io = new File(ioDir, testDataFile + ".json");
		try {
			String json = Files.contentOf(context.getResource("classpath:" + io.getPath()).getFile(), "UTF-8");
			System.out.println("json " + json);
			return json;
		} catch (IOException e) {
			throw new TestDataParsingException(testDataFile, e);
		}
	}

	private void performResultMatch(final ResultActions resultActions, String jsonOutput) {
		Optional.ofNullable(jsonOutput).ifPresent(c -> matchJson(resultActions, c));
	}

	private void matchJson(final ResultActions resultActions, String jsonOutput) {
		try {
			resultActions.andExpect(MockMvcResultMatchers.content().json(jsonOutput));
		} catch (Exception e) {
			throw new AssertionException("Cannot perform response match", e);
		}
	}

	private RequestBuilder populateRequestBuilder(String uri, HttpMethod httpMethod, String jsonInput,
			Map<String, String> headers) {
		MockHttpServletRequestBuilder requestBuilder = map.get(httpMethod).apply(uri);
		Optional.ofNullable(jsonInput).ifPresent(c -> requestBuilder.content(c).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		Optional.ofNullable(headers)
				.ifPresent(c -> c.entrySet().forEach(e -> requestBuilder.header(e.getKey(), e.getValue())));
		return requestBuilder;
	}

	private void performResultActionStatus(HttpStatus httpStatus, ResultActions resultActions) throws Exception {
		Optional.ofNullable(resultActionStatusMap.get(httpStatus)).ifPresent(c -> {
			try {
				resultActions.andExpect(c);
			} catch (Exception e) {
				throw new AssertionException("Cannot perform HTTP Status match", e);
			}
		});
	}
}
