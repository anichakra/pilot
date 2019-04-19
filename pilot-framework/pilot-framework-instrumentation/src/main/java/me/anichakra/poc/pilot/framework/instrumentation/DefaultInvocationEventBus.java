package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is a implementation of {@link InvocationEventBus} for
 * {@link InvocationEvent} instances. This contains a list of
 * {@link InvocationEventHandler}s that is registered from external component.
 *
 * 
 * @author anichakra
 *
 */
@Component
public class DefaultInvocationEventBus implements InvocationEventBus {

	private List<InvocationEventHandler> conversationEventHandlers;

	/**
	 * Creates a InvocationEventBus along with an empty list of
	 * {@link InvocationEventHandler}
	 */
	public DefaultInvocationEventBus() {
		conversationEventHandlers = new ArrayList<InvocationEventHandler>();
	}

	private void registerInvocationEventHandler(InvocationEventHandler conversationEventHandler) {
		this.conversationEventHandlers.add(conversationEventHandler);
	}


	@Override
	public void fireInvocationEvent(InvocationEvent invocationEvent) {
		for (InvocationEventHandler conversationEventHandler : conversationEventHandlers) {
			conversationEventHandler.handleInvocationEvent(invocationEvent);
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
		for (InvocationEventHandler conversationEventHandler : conversationEventHandlers) {
			conversationEventHandler.clear();
		}
		InvocationEvent.clearCurrent();
	}

	@Override
	@Autowired
	public void setInvocationEventHandlers(List<InvocationEventHandler> conversationEventHandlers) {
		for (InvocationEventHandler conversationEventHandler : conversationEventHandlers) {
			if (conversationEventHandler.isEnabled()) {
				registerInvocationEventHandler(conversationEventHandler);
			}
		}
	}

	@Override
	public void unregisterInvocationEventHandler(InvocationEventHandler conversationEventHandler) {
		conversationEventHandlers.remove(conversationEventHandler);
	}
}
