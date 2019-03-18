package me.anichakra.poc.pilot.framework.test.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * This implements the ApiCallable for Rest/Json based API calls. This class
 * uses Spring MVC test framework to create a more opinionated test framework.
 * 
 * @see MockMvc
 * @author anirbanchakraborty
 *
 */
public class ReSTJsonBasedApiCallable implements ApiCallable {
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

	private static final Map<HttpMethod, Function<URI, MockHttpServletRequestBuilder>> httpMethodFunctionMap = new HashMap<>();

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
		// TODO: couple of optional methods are left to be implemented.
	}
	static {
		// TODO: might need to add more as and when required!
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
	private String uri;
	private HttpMethod method;
	private String fileName;
	private Object[] uriVariables;
	private MockMvc mockMvc;

	/**
	 * Creates a new instance using {@link MockMvc}, the URI of the API and the HTTP
	 * method.
	 * 
	 * @param mockMvc
	 * @param uri
	 * @param method
	 */
	public ReSTJsonBasedApiCallable(MockMvc mockMvc, String uri, HttpMethod method) {
		this.mockMvc = mockMvc;
		this.uri = uri;
		this.method = method;
	}

	/**
	 * 
	 * @return
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * 
	 * @return
	 */
	public HttpMethod getMethod() {
		return method;
	}

	/**
	* 
	*/
	@Override
	public ApiCallable setUriVariables(Object... uriVariables) {
		this.uriVariables = uriVariables;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	Object[] getUriVariables() {
		return uriVariables;
	}

	/**
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 
	 */
	@Override
	public <T> ApiResult<T> call(RequestBody requestBody) {
		return call(requestBody, null);
	}

	/**
	 * 
	 */
	@Override
	public <T> ApiResult<T> call(RequestBody requestBody, RequestHeaders requestHeaders) {
		RequestBuilder requestBuilder = populateRequestBuilder(uri, method, requestBody, requestHeaders);
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
	 */
	@Override
	public <T> ApiResult<T> call() {
		return call(null, null);
	}

	private RequestBuilder populateRequestBuilder(String uri, HttpMethod httpMethod, RequestBody requestBody,
			RequestHeaders requestHeaders) {
		MockHttpServletRequestBuilder requestBuilder = httpMethodFunctionMap.get(httpMethod)
				.apply(new URI(uri, uriVariables));
		Optional.ofNullable(requestBody).ifPresent(c -> requestBuilder.content(c.getJson())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		Optional.ofNullable(requestHeaders).ifPresent(
				c -> c.getHeaders().entrySet().forEach(e -> requestBuilder.header(e.getKey(), e.getValue())));
		return requestBuilder;
	}
}
