package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.List;

public interface InvocationEventBus {

	/**
	 * Externally this Invocation will be fired. All the
	 * {@link InvocationEventHandler}s will be handling the conversation to either
	 * log it, or set to JMX MBean for monitoring etc.
	 */
	void fireInvocationEvent(InvocationEvent invocationEvent);

	/**
	 * Clears all the {@link InvocationEventHandler}s registered.
	 */
	void clearAll();

	/**
	 * Sets the {@link InvocationEventHandler}s
	 * 
	 * @param conversationEventHandlers
	 */
	void setInvocationEventHandlers(List<InvocationEventHandler<InvocationEvent>> conversationEventHandlers);

	/**
	 * Dynamically any {@link InvocationEventHandler} can be unregistered from the
	 * bus.
	 */
	void unregisterInvocationEventHandler(InvocationEventHandler<InvocationEvent> conversationEventHandler);

}