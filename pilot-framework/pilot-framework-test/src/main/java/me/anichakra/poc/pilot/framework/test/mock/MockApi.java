package me.anichakra.poc.pilot.framework.test.mock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

@Component
@WebAppConfiguration
public class MockApi {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private Map<HttpStatus, ResultMatcher> resultActionStatusMap = new HashMap<HttpStatus, ResultMatcher>();

	boolean isInitialize = false;

	public void init() {
		isInitialize = true;
		initResultActionStatusMap();

	}

	private void initResultActionStatusMap() {
		resultActionStatusMap.put(HttpStatus.OK, status().isOk());
		resultActionStatusMap.put(HttpStatus.ACCEPTED, status().isAccepted());
		resultActionStatusMap.put(HttpStatus.CREATED, status().isCreated());
		resultActionStatusMap.put(HttpStatus.UNAUTHORIZED, status().isUnauthorized());
		resultActionStatusMap.put(HttpStatus.FOUND, status().isFound());
		resultActionStatusMap.put(HttpStatus.FORBIDDEN, status().isForbidden());
	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus) throws Exception {
		assertCall(url, httpMethod, httpStatus, null);
	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, Map<String, String> headers)
			throws Exception {
		if (!isInitialize) {
			init();
		}
		RequestBuilder requestBuilder = null;
		requestBuilder = populateRequestBuilder(url, httpMethod, requestBuilder, null, headers);

		ResultActions resultActions = mockMvc.perform(requestBuilder).andDo(print());
		performResultActionStatus(httpStatus, resultActions);

	}

	public void assertCallWithJSON(String url, HttpMethod httpMethod, HttpStatus httpStatus, String jsonInput,
			String jsonOutput) throws Exception {
		assertCallWithJSON(url, httpMethod, httpStatus, jsonInput, jsonOutput, null);
	}

	public void assertCallWithJSON(String url, HttpMethod httpMethod, HttpStatus httpStatus, String jsonInput,
			String jsonOutput, Map<String, String> headers) throws Exception {
		if (!isInitialize) {
			init();
		}
		RequestBuilder requestBuilder = null;
		requestBuilder = populateRequestBuilder(url, httpMethod, requestBuilder, jsonInput, headers);
		ResultActions resultActions = mockMvc.perform(requestBuilder);
		resultActions = performResultActionStatus(httpStatus, resultActions);
		resultActions = performResultMatch(jsonOutput, resultActions);
	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, String jsonInputFile,
			String jsonOutputFile) throws Exception {
		assertCall(url, httpMethod, httpStatus, jsonInputFile, jsonOutputFile, null);

	}

	public void assertCall(String url, HttpMethod httpMethod, HttpStatus httpStatus, String jsonInputFile,
			String jsonOutputFile, Map<String, String> headers) throws Exception {
		if (!isInitialize) {
			init();
		}
		RequestBuilder requestBuilder = null;
		String jsonInput = null, jsonOutput = null;
		if (!StringUtils.isEmpty(jsonInputFile)) {
			// jsonInput =
			// Files.contentOf(resourceUtils.getFile("classpath:"+jsonInputFile), "UTF-8");
			jsonInputFile = "io/" + jsonInputFile + ".json";
			jsonInput = Files.contentOf(context.getResource("classpath:" + jsonInputFile).getFile(), "UTF-8");
		}
		if (!StringUtils.isEmpty(jsonOutputFile)) {
			// jsonOutput =
			// Files.contentOf(resourceUtils.getFile("classpath:"+jsonOutputFile), "UTF-8");
			jsonOutputFile = "io/" + jsonOutputFile + ".json";
			jsonOutput = Files.contentOf(context.getResource("classpath:" + jsonOutputFile).getFile(), "UTF-8");
		}
		requestBuilder = populateRequestBuilder(url, httpMethod, requestBuilder, jsonInput, headers);
		ResultActions resultActions = mockMvc.perform(requestBuilder);
		resultActions = performResultActionStatus(httpStatus, resultActions);
		resultActions = performResultMatch(jsonOutput, resultActions);
	}

	private ResultActions performResultMatch(String jsonOutput, ResultActions resultActions) throws Exception {
		ResultActions resultActionsReturn = null;
		if (!StringUtils.isEmpty(jsonOutput)) {
			// resultActionsReturn =
			// resultActions.andExpect(MockMvcResultMatchers.content().json("[" + jsonOutput
			// + "]"));
			resultActionsReturn = resultActions.andExpect(MockMvcResultMatchers.content().json(jsonOutput));
		} else {
			resultActionsReturn = resultActions;
		}
		return resultActionsReturn;
	}

	private RequestBuilder populateRequestBuilder(String url, HttpMethod httpMethod, RequestBuilder requestBuilder,
			String jsonInput, Map<String, String> headers) {
		switch (httpMethod) {
		case GET:
			requestBuilder = get(url);
			break;
		case POST:
			if (!StringUtils.isEmpty(jsonInput)) {
				requestBuilder = post(url).content(jsonInput).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON);
			} else {
				requestBuilder = post(url);
			}
			break;
		case PUT:
			if (!StringUtils.isEmpty(jsonInput)) {
				requestBuilder = put(url).content(jsonInput).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON);
			} else {
				requestBuilder = put(url);
			}
			break;
		case DELETE:
			requestBuilder = delete(url);
			break;
		case HEAD:
			requestBuilder = head(url);
			break;
		case PATCH:
			requestBuilder = head(url);
			break;
		case OPTIONS:
			requestBuilder = head(url);
			break;
		default:
			// throw new SystemException("Method not supported");
			throw new RuntimeException("Method not supported");
		}
		HttpHeaders httpHeaders = getHttpHeaders(headers);
		if (null != httpHeaders) {
			MockHttpServletRequestBuilder mockHttpServletRequestBuilder = (MockHttpServletRequestBuilder) requestBuilder;
			requestBuilder = mockHttpServletRequestBuilder.headers(httpHeaders);
		}
		return requestBuilder;
	}

	private HttpHeaders getHttpHeaders(Map<String, String> headers) {
		HttpHeaders httpHeaders = null;
		if (null != headers && !CollectionUtils.isEmpty(headers.keySet())) {
			httpHeaders = new HttpHeaders();
			for (String headerName : headers.keySet()) {
				httpHeaders.add(headerName, headers.get(headerName));
			}
		}
		return httpHeaders;
	}

	private ResultActions performResultActionStatus(HttpStatus httpStatus, ResultActions resultActions)
			throws Exception {
		ResultActions resultActionReturn = null;
		if (resultActionStatusMap.containsKey(httpStatus)) {
			resultActionReturn = resultActions.andExpect(resultActionStatusMap.get(httpStatus));
		} else {
			resultActionReturn = resultActions;
		}
		return resultActionReturn;
	}

}
