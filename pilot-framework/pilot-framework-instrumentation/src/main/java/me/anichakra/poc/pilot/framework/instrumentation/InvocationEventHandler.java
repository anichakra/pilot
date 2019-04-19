package me.anichakra.poc.pilot.framework.instrumentation;

/**
 * An InvocationEventHandler is suppose to handle an {@link InvocationEvent}.
 * All implemented handlers can be added to the framework as pluggable components.
 * components.
 * 
 * @author anichakra

 */
public interface InvocationEventHandler {

	/**
	 * Handle the invocation event to do something like logging, tracing, emailing,
	 * etc.
	 * 
	 * @param event
	 */
	void handleInvocationEvent(InvocationEvent event);

	/**
	 * Clear the event from the context. Do any clean up activity. After the
	 * invocation is ended this method is called.
	 */
	void clear();

	/**
	 * 
	 * @return Returns the events if present or null
	 */
	boolean isEnabled();

}
