package me.anichakra.poc.pilot.framework.instrumentation.handler;

import static me.anichakra.poc.pilot.framework.instrumentation.InvocationLineItem.SEPARATOR;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.AbstractInvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBuilder;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationLineItem;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationStatus;

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
@ConfigurationProperties(prefix = "instrumentation.handlers.log")
public class LoggingEventHandler extends AbstractInvocationEventHandler {
	public static final String LOGGER_NAME = "INSTRUMENTATION_LOG";
	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);
	private boolean ignoreParameters;
	private boolean ignoreExceptionStack;
	private boolean ignoreOutcome;
	private boolean limited;

	public boolean isLimited() {
		return limited;
	}

	public void setLimited(boolean limited) {
		this.limited = limited;
	}

	public void setIgnoreParameters(boolean ignoreParameters) {
		this.ignoreParameters = ignoreParameters;
	}

	public void setIgnoreExceptionStack(boolean ignoreExceptionStack) {
		this.ignoreExceptionStack = ignoreExceptionStack;
	}

	public void setIgnoreOutcome(boolean ignoreOutcome) {
		this.ignoreOutcome = ignoreOutcome;
	}

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
		if (!super.getLayers().contains(event.getInvocationLineItem().getLayer()))
			return; // if any layer is not mentioned do not log
		StringBuilder sb = new StringBuilder();
		InvocationLineItem metric = event.getInvocationLineItem();
		if (ignoreParameters)
			metric.setArguments(null);

		if (ignoreOutcome)
			metric.setOutcome(null);
		sb.append(event.getEventId()).append(SEPARATOR);

		if (!limited || (metric.getInvocationStatus().equals(InvocationStatus.Started))) { // for limited the completed
																							// and failed status
																							// invocation line items
																							// should show limited
																							// metrics.
			event.getMetrics().entrySet().forEach(e -> sb.append(e.getValue()).append(SEPARATOR));
		}
		sb.append(metric);
		if (metric.getRootCause() == null)
			logger.info(sb);
		else
			logger.info(sb, extractRootCause(metric.getRootCause()));
	}

	private Throwable extractRootCause(Throwable rootCause) {
		if (ignoreExceptionStack)
			return NestedExceptionUtils.getRootCause(rootCause);
		else
			return rootCause;
	}

	/**
	 * Clears the stack from {@link ThreadContext}
	 */
	public void clear() {
		ThreadContext.clearStack();
	}

}
