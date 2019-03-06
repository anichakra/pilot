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
import java.util.Map.Entry;
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

@Component
@WebAppConfiguration
public class MockApi {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private static Map<HttpStatus, ResultMatcher> resultActionStatusMap = new HashMap<HttpStatus, ResultMatcher>();

	boolean isInitialize = false;

	private static final Map<HttpMethod, Function<String, MockHttpServletRequestBuilder>> map = new HashMap<>();

	static {

		resultActionStatusMap.put(HttpStatus.OK, status().isOk());
		resultActionStatusMap.put(HttpStatus.ACCEPTED, status().isAccepted());
		resultActionStatusMap.put(HttpStatus.CREATED, status().isCreated());
		resultActionStatusMap.put(HttpStatus.UNAUTHORIZED, status().isUnauthorized());
		resultActionStatusMap.put(HttpStatus.FOUND, status().isFound());
		resultActionStatusMap.put(HttpStatus.FORBIDDEN, status().isForbidden());

		Function<String, MockHttpServletRequestBuilder> getFunc = (String url) -> {
			return get(url);
		};
		Function<String, MockHttpServletRequestBuilder> postFunc = (String url) -> {
			return post(url);
		};
		Function<String, MockHttpServletRequestBuilder> putFunc = (String url) -> {
			return put(url);
		};
		Function<String, MockHttpServletRequestBuilder> deleteFunc = (String url) -> {
			return delete(url);
		};
		map.put(HttpMethod.GET, getFunc);
		map.put(HttpMethod.POST, postFunc);
		map.put(HttpMethod.PUT, putFunc);
		map.put(HttpMethod.DELETE, deleteFunc);
	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus) throws Exception {
		assertCall(url, httpMethod, httpStatus, null);
	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, Map<String, String> headers)
			throws Exception {
		procerssCall(url, httpMethod, httpStatus, headers, null, null, null);
	}

	public void assertCallWithJSON(String url, HttpMethod httpMethod, HttpStatus httpStatus, String jsonInput,
			String jsonOutput) throws Exception {
		assertCallWithJSON(url, httpMethod, httpStatus, jsonInput, jsonOutput, null);
	}

	public void assertCallWithJSON(String url, HttpMethod httpMethod, HttpStatus httpStatus, String jsonInput,
			String jsonOutput, Map<String, String> headers) throws Exception {
		procerssCall(url, httpMethod, httpStatus, headers, jsonInput, jsonOutput, null);
	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, String ioFile,
			String jsonOutputFile) throws Exception {
		assertCall(url, httpMethod, httpStatus, null, ioFile, jsonOutputFile);

	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, Map<String, String> headers,
			String ioFile, String jsonOutputFile) throws Exception {
		String jsonInput = readJSONDataFromFile(ioFile);
		String jsonOutput = readJSONDataFromFile(jsonOutputFile);
		procerssCall(url, httpMethod, httpStatus, headers, jsonInput, jsonOutput, null);
	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, Map<String, String> headers,
			String jsonIntputFile, String jsonOutputFile, Map<String, Object> inclusionCheck) throws Exception {
		String jsonInput = readJSONDataFromFile(jsonIntputFile);
		String jsonOutput = readJSONDataFromFile(jsonOutputFile);
		procerssCall(url, httpMethod, httpStatus, headers, jsonInput, jsonOutput, inclusionCheck);
	}

	private void procerssCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, Map<String, String> headers,
			String jsonInput, String jsonOutput, Map<String, Object> inclusionCheck) throws Exception {
		RequestBuilder requestBuilder = populateRequestBuilder(url, httpMethod, jsonInput, headers);
		ResultActions resultActions = mockMvc.perform(requestBuilder).andDo(print());
		performResultActionStatus(httpStatus, resultActions);
		performResultMatch(resultActions, jsonOutput, inclusionCheck);
	}

	private String readJSONDataFromFile(String ioFile) throws IOException {
		
		return Optional.ofNullable(ioFile).map(c -> loadIoFile(c)).orElse(null);
	}

	private String loadIoFile(String ioFile) {
		System.out.println("ioFile "+ioFile);
		File ioDir = new File("io");
		File io = new File(ioDir,ioFile+".json");
		try {
			String json =  Files.contentOf(context.getResource("classpath:" + io.getPath()).getFile(), "UTF-8");
			System.out.println("json "+json);
			return json;
		} catch (IOException e) {
			throw new IOFileNotFoundException(ioFile, e);
		}
	}

	private void performResultMatch(final ResultActions resultActions, String jsonOutput,
			Map<String, Object> inclusionCheck) throws Exception {
		Optional.ofNullable(jsonOutput).ifPresent(c -> matchJson(resultActions, c));

		Optional.ofNullable(inclusionCheck)
				.ifPresent(c -> c.entrySet().forEach(e -> validateJson(resultActions, jsonOutput, e)));
	}

	private void matchJson(final ResultActions resultActions, String jsonOutput) {
		try {
			resultActions.andExpect(MockMvcResultMatchers.content().json(jsonOutput));
		} catch (Exception e) {
			throw new RuntimeException(jsonOutput, e);
		}
	}

	private void validateJson(final ResultActions resultActions, String jsonOutput, Entry<String, Object> e) {
		try {
			resultActions.andExpect(MockMvcResultMatchers.jsonPath("$." + e.getKey()).value(e.getValue()));
		} catch (Exception e1) {
			throw new RuntimeException(jsonOutput, e1);
		}
	}

	private RequestBuilder populateRequestBuilder(String url, HttpMethod httpMethod, String jsonInput,
			Map<String, String> headers) {
		MockHttpServletRequestBuilder requestBuilder = map.get(httpMethod).apply(url);
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
				throw new RuntimeException(e);
			}
		});
	}
}
