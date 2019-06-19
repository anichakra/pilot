package me.anichakra.poc.pilot.framework.instrumentation;

/**
 * The metrics as part of an invocation
 * @author anirbanchakraborty
 *
 */
public enum InvocationMetric {
	/**
	 * The name of the web container or microservice
	 */
	NAME,
	/**
	 * The version of the container or microservice
	 */
	VERSION,
	/**
	 * The start time of the container
	 */
	START_TIME, 
	/**
	 * The environment where the container is running, like DEV, TEST, STAGE, PROD etc
	 */
	ENVIRONMENT,
	/**
	 * The instance id, or node id, etc
	 */
	INSTANCE_ID, 
	/**
	 * The unique id for a user session or a group of multiple requests as part of a users transaction to the system
	 */
	TRACE_ID,
	/**
	 * The unique id that is transferred as part of request header from one invocation to another to make a correlation
	 */
	CORRELATION_ID, 
	/**
	 * The user id for the user or another application/system who/which is invoking the invocation
	 */
	USER_ID, 
	/**
	 * The local address of the container
	 */
	LOCAL_ADDRESS, 
	/**
	 * The remote address from where the invocation is made
	 */
	REMOTE_ADDRESS, 
	/**
	 * The URI of the API that is invoked
	 */
	URI
}
