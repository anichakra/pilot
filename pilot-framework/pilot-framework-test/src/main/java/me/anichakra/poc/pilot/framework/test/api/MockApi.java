package me.anichakra.poc.pilot.framework.test.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This mocks a microservice environment based on JSON based ReST API. Using
 * this one can declaratively compose a test method by mentioning only the API
 * URI. This uses builder pattern, hence from mocking to calling the mock and
 * assertion of the API result can be done declaratively.
 * 
 * Inject this class inside the test class. Object of this class is thread-safe.
 * 
 * @author anirbanchakraborty
 *
 */
@Component
@WebAppConfiguration
@Profile("test")
public class MockApi {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Mocks a ReST API post URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	public ApiCallable post(String uri) throws Exception {
		return new ReSTJsonBasedApiCallable(mockMvc, uri, HttpMethod.POST);
	}

	/**
	 * Mocks a ReST API delete URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	public ApiCallable delete(String uri) {
		return new ReSTJsonBasedApiCallable(mockMvc, uri, HttpMethod.DELETE);
	}

	/**
	 * Mocks a ReST API get URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	public ApiCallable get(String uri) {
		return new ReSTJsonBasedApiCallable(mockMvc, uri, HttpMethod.GET);
	}

	/**
	 * Mocks a ReST API put URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	public ApiCallable put(String uri) {
		return new ReSTJsonBasedApiCallable(mockMvc, uri, HttpMethod.PUT);
	}

}
