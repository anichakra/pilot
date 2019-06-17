package me.anichakra.poc.pilot.framework.instrumentation;

public enum InvocationMetric {
	NAME, VERSION, START_TIME, INSTANCE_ID, SESSION_ID,
	/**
	 * Sets the correlation id to the invocation. The correlation id is the
	 * 
	 * invocation id of a parent invocation. For e.g if a web application is
	 * executing a invocation which invokes a ReST call to another application, then
	 * the invocation id is passed in the ReST call, so that the new invocation that
	 * is starting in the second application exposing ReST service will send the
	 * invocation id as correlation id to its Invocation instance.
	 */
	EVENT_ID, CORRELATION_ID, USER_ID, LOCAL_ADDRESS, REMOTE_ADDRESS, URI
}
