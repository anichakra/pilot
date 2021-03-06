package me.anichakra.poc.pilot.framework.instrumentation.handler;

import java.util.Optional;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.newrelic.api.agent.NewRelic;

import me.anichakra.poc.pilot.framework.instrumentation.AbstractInvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBuilder;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationLineItem;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric;

/**
 * Log4j2 Implementation of {@link InvocationEventHandler} for writing log. When
 * a Conversation is published in the InvocationEventBus this handler if
 * registered in the bus will be invoked.
 * <p>
 * 
 * @see InvocationEventBuilder
 * @see InvocationEventBus
 * @author anichakra
 *
 */
@Component
@ConfigurationProperties(prefix = "instrumentation.handlers.new-relic")
public class NewRelicEventHandler extends AbstractInvocationEventHandler {

	/**
	 * Writes current {@link InvocationLineItem} of the passed
	 * {@link InvocationEventBuilder} object to log in info mode. Also put the entire
	 * conversation instance to {@link ThreadContext} with a key 'event'.
	 * <p>
	 * The corresponding log4j pattern layout recommended is:
	 * <code> pattern="%d [%t] %-5p %c-%X{event};%m%n" </code>
	 */
	@Override
	public void handleInvocationEvent(InvocationEvent event) {
		NewRelic.addCustomParameter("transactionId",
				Optional.ofNullable(event.getMetrics().get(InvocationMetric.CORRELATION_ID)).orElse(event.getEventId()));
	}

	/**
	 * Clears the stack from {@link ThreadContext}
	 */
	public void clear() {
		ThreadContext.clearStack();
	}

}
