package me.anichakra.poc.pilot.framework.instrumentation;

/**
 * The different well defined layers where instrumentation can work.
 * 
 * @author anirbanchakraborty
 *
 */
public enum Layer {
	/**
	 * The web filter layer which is the entry point for all web invocation, e.g.
	 * any ReST call.
	 */
	FILTER,

	/**
	 * The controller layer exposes a Web API as ReST service. Controller invokes a
	 * Service.
	 */
	CONTROLLER,

	/**
	 * The service layer processes all business logic ands invokes another service
	 * or repository.
	 */
	SERVICE,

	/**
	 * The repository communicates with the underlying persistence tier.
	 */
	REPOSITORY
}
