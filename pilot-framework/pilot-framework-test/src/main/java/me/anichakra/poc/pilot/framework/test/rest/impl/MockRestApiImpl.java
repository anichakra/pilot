package me.anichakra.poc.pilot.framework.test.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import me.anichakra.poc.pilot.framework.test.rest.api.MockRestApi;
import me.anichakra.poc.pilot.framework.test.rest.api.RestApiCallable;

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
@ConfigurationProperties(prefix = "test")
public class MockRestApiImpl implements MockRestApi {

	private boolean print = false;

	public void setPrint(boolean print) {
		this.print = print;
	}

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Mocks a ReST API post URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	@Override
	public RestApiCallable post(String uri) throws Exception {
		return new RestJsonBasedApiCallable(mockMvc, uri, HttpMethod.POST, print);
	}

	/**
	 * Mocks a ReST API delete URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	@Override
	public RestApiCallable delete(String uri) {
		return new RestJsonBasedApiCallable(mockMvc, uri, HttpMethod.DELETE, print);
	}

	/**
	 * Mocks a ReST API get URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	@Override
	public RestApiCallable get(String uri) {
		return new RestJsonBasedApiCallable(mockMvc, uri, HttpMethod.GET, print);
	}

	/**
	 * Mocks a ReST API put URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	@Override
	public RestApiCallable put(String uri) {
		return new RestJsonBasedApiCallable(mockMvc, uri, HttpMethod.PUT, print);
	}

}
