package me.anichakra.poc.pilot.framework.test.api;

/**
 * Using MockAPI an URI of an API environment can be mocked. The actual
 * implementation of API can be called using this interface. The implementation
 * class must implement the overloaded call() methods to invoke the APIs.
 * 
 * @author anirbanchakraborty
 *
 */
public interface ApiCallable {

	/**
	 * Sets the URI variables to this instance. The URI variables can be path
	 * variables or query params. Passing URI variables is options. E.g.
	 * <p>
	 * As part of the path variables:
	 * <pre>
	 * <code>
	 * mockApi.delete("/vehicle?id={id}").setUriVariables(v.getId()).call()
	 * </code>
	 * </pre>
	 * <p>
	 * As part of the query params:
	 * <pre>
	 * <code>
	 * mockApi.get("/vehicle/search?manufacturer={manufacturer}").setUriVariables("Nissan").call()
	 * </code>
	 * </pre>
	 * @param uriVariables
	 * @return
	 */
	ApiCallable setUriVariables(Object... uriVariables);

	/**
	 * Calls an API providing the request body
	 * 
	 * @param requestBody
	 * @return The ApiResult that contains the actual results from the API call.
	 */
	<T> ApiResult<T> call(RequestBody requestBody);

	/**
	 * Calls an API providing the request body and the request headers
	 * 
	 * @param requestBody
	 * @return The ApiResult that contains the actual results from the API call.
	 */
	<T> ApiResult<T> call(RequestBody requestBody, RequestHeaders requestHeaders);

	/**
	 * Calls an API providing no request body or headers.
	 * 
	 * @param requestBody
	 * @return The ApiResult that contains the actual results from the API call.
	 */
	<T> ApiResult<T> call();

}