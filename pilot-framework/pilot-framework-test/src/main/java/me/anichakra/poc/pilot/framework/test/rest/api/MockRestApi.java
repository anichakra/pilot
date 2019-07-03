package me.anichakra.poc.pilot.framework.test.rest.api;

public interface MockRestApi {

	/**
	 * Mocks a ReST API post URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	RestApiCallable post(String uri) throws Exception;

	/**
	 * Mocks a ReST API delete URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	RestApiCallable delete(String uri);

	/**
	 * Mocks a ReST API get URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	RestApiCallable get(String uri);

	/**
	 * Mocks a ReST API put URI of the API to be called or tested.
	 * 
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	RestApiCallable put(String uri);

}