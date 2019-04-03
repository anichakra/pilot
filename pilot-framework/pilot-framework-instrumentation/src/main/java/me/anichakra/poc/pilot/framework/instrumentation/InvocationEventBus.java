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
public class InvocationEventBus {

    private List<InvocationEventHandler<InvocationEvent>> conversationEventHandlers;

    /**
     * Creates a InvocationEventBus along with an empty list of
     * {@link InvocationEventHandler}
     */
    public InvocationEventBus() {
        conversationEventHandlers = new ArrayList<InvocationEventHandler<InvocationEvent>>();
    }

    private void registerInvocationEventHandler(InvocationEventHandler<InvocationEvent> conversationEventHandler) {
        this.conversationEventHandlers.add(conversationEventHandler);
    }

    /**
     * Externally this Invocation will be fired. All the
     * {@link InvocationEventHandler}s will be handling the conversation to either
     * log it, or set to JMX MBean for monitoring etc.
     */
    public void fireInvocationEvent(InvocationEvent invocationEvent) {
        for (InvocationEventHandler<InvocationEvent> conversationEventHandler : conversationEventHandlers) {
            conversationEventHandler.handleInvocationEvent(invocationEvent);
        }
    }

    /**
     * Clears all the {@link InvocationEventHandler}s registered.
     */
    public void clearAll() {
        for (InvocationEventHandler<InvocationEvent> conversationEventHandler : conversationEventHandlers) {
            conversationEventHandler.clear();
        }
        InvocationEvent.clearCurrent();
    }

    /**
     * Sets the {@link InvocationEventHandler}s
     * 
     * @param conversationEventHandlers
     */
    @Autowired
    public void setInvocationEventHandlers(List<InvocationEventHandler<InvocationEvent>> conversationEventHandlers) {
    	for (InvocationEventHandler<InvocationEvent> conversationEventHandler : conversationEventHandlers) {
            if (conversationEventHandler.isEnabled()) {
                registerInvocationEventHandler(conversationEventHandler);
            }
        }
    }

    /**
     * Dynamically any {@link InvocationEventHandler} can be unregistered from the
     * bus.
     */
    public void unregisterInvocationEventHandler(InvocationEventHandler<InvocationEvent> conversationEventHandler) {
        conversationEventHandlers.remove(conversationEventHandler);
    }
}
