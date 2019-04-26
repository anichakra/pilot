package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.AbstractInvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;

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
public class DefaultInvocationEventBus extends AbstractInvocationEventBus {

	@Autowired
	public void setInvocationEventHandlers(List<InvocationEventHandler> invocationEventHandlers) {
		for (InvocationEventHandler invocationEventHandler : invocationEventHandlers) {
			if (invocationEventHandler.isEnabled()) {
				super.registerInvocationEventHandler(invocationEventHandler);
			}
		}
	}

	
}
