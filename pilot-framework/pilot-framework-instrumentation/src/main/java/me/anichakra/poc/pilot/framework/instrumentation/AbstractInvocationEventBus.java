package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a implementation of {@link InvocationEventBus} for
 * {@link InvocationEventBuilder} instances. This contains a list of
 * {@link InvocationEventHandler}s that is registered from external component.
 *
 * 
 * @author anichakra
 *
 */
public abstract class AbstractInvocationEventBus implements InvocationEventBus {

	private List<InvocationEventHandler> invocationEventHandlers;

	/**
	 * Creates a InvocationEventBus along with an empty list of
	 * {@link InvocationEventHandler}
	 */
	public AbstractInvocationEventBus() {
		invocationEventHandlers = new ArrayList<InvocationEventHandler>();
	}

	@Override
	public void fireInvocationEvent(InvocationEventBuilder invocationEventBuilder) {
		for (InvocationEventHandler conversationEventHandler : invocationEventHandlers) {
			conversationEventHandler.handleInvocationEvent(invocationEventBuilder.createInvocationEvent());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.anichakra.poc.pilot.framework.instrumentation.aspect.InvocationEventBus#
	 * clearAll()
	 */
	@Override
	public void clearAll() {
		for (InvocationEventHandler conversationEventHandler : invocationEventHandlers) {
			conversationEventHandler.clear();
		}
		InvocationEventBuilder.clearCurrent();
	}

	@Override
	public void unregisterInvocationEventHandler(InvocationEventHandler conversationEventHandler) {
		invocationEventHandlers.remove(conversationEventHandler);
	}

	protected void registerInvocationEventHandler(InvocationEventHandler invocationEventHandler) {
		this.invocationEventHandlers.add(invocationEventHandler);
	}

}
