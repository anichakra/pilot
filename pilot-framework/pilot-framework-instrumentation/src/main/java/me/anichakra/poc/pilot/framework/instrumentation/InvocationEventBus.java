package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.List;

/**
 * The InvocationEventBus is responsible to register
 * {@link InvocationEventHandler}s and then can fire an {@link InvocationEventBuilder}
 * on all the handlers.
 * 
 * @author anirbanchakraborty
 *
 */
public interface InvocationEventBus {

	/**
	 * All the {@link InvocationEventHandler}s will be handling the
	 * {@link InvocationEventBuilder} to do some meaningful work like publishing to
	 * messaging engine, logging, emailing etc.
	 */
	void fireInvocationEvent(InvocationEventBuilder invocationEvent);

	/**
	 * Clears all the {@link InvocationEventHandler}s registered.
	 */
	void clearAll();

	/**
	 * Sets the {@link InvocationEventHandler}s
	 * 
	 * @param invocationEventHandler
	 */
	void setInvocationEventHandlers(List<InvocationEventHandler> invocationEventHandler);

	/**
	 * Dynamically any {@link InvocationEventHandler} can be unregistered from the
	 * bus.
	 */
	void unregisterInvocationEventHandler(InvocationEventHandler invocationEventHandler);

}