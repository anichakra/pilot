package me.anichakra.poc.pilot.framework.test.api;

public interface MockApi {

	/**
	 * Mocks a ReST API post URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	ApiCallable post(String uri) throws Exception;

	/**
	 * Mocks a ReST API delete URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	ApiCallable delete(String uri);

	/**
	 * Mocks a ReST API get URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	ApiCallable get(String uri);

	/**
	 * Mocks a ReST API put URI of the API to be called or tested.
	 * @param uri The URI to mock.
	 * @return An ApiCallable instance for the URI provided
	 * @throws Exception
	 */
	ApiCallable put(String uri);

}