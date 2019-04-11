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

	private List<InvocationEventHandler<InvocationEvent>> conversationEventHandlers;

	/**
	 * Creates a InvocationEventBus along with an empty list of
	 * {@link InvocationEventHandler}
	 */
	public DefaultInvocationEventBus() {
		conversationEventHandlers = new ArrayList<InvocationEventHandler<InvocationEvent>>();
	}

	private void registerInvocationEventHandler(InvocationEventHandler<InvocationEvent> conversationEventHandler) {
		this.conversationEventHandlers.add(conversationEventHandler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.anichakra.poc.pilot.framework.instrumentation.aspect.InvocationEventBus#
	 * fireInvocationEvent(me.anichakra.poc.pilot.framework.instrumentation.
	 * InvocationEvent)
	 */
	@Override
	public void fireInvocationEvent(InvocationEvent invocationEvent) {
		for (InvocationEventHandler<InvocationEvent> conversationEventHandler : conversationEventHandlers) {
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
		for (InvocationEventHandler<InvocationEvent> conversationEventHandler : conversationEventHandlers) {
			conversationEventHandler.clear();
		}
		InvocationEvent.clearCurrent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.anichakra.poc.pilot.framework.instrumentation.aspect.InvocationEventBus#
	 * setInvocationEventHandlers(java.util.List)
	 */
	@Override
	@Autowired
	public void setInvocationEventHandlers(List<InvocationEventHandler<InvocationEvent>> conversationEventHandlers) {
		for (InvocationEventHandler<InvocationEvent> conversationEventHandler : conversationEventHandlers) {
			if (conversationEventHandler.isEnabled()) {
				registerInvocationEventHandler(conversationEventHandler);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.anichakra.poc.pilot.framework.instrumentation.aspect.InvocationEventBus#
	 * unregisterInvocationEventHandler(me.anichakra.poc.pilot.framework.
	 * instrumentation.InvocationEventHandler)
	 */
	@Override
	public void unregisterInvocationEventHandler(InvocationEventHandler<InvocationEvent> conversationEventHandler) {
		conversationEventHandlers.remove(conversationEventHandler);
	}
}
