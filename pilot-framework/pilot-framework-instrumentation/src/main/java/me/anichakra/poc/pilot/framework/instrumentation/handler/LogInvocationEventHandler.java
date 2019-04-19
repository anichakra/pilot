package me.anichakra.poc.pilot.framework.instrumentation.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.AbstractInvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric;

/**
 * Log4j2 Implementation of {@link InvocationEventHandler} for writing log. When
 * a Conversation is published in the InvocationEventBus this handler if
 * registered in the bus will be invoked.
 * <p>
 * 
 * @see InvocationEvent
 * @see InvocationEventBus
 * @author anichakra
 *
 */
@Component
@ConfigurationProperties(prefix = "instrumentation.handlers.log")
public class LogInvocationEventHandler extends AbstractInvocationEventHandler {
	public static final String LOGGER_NAME = "INSTRUMENTATION_LOG";
	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);

	public void setIgnoreUriVariables(boolean ignoreUriVariables) {
		this.ignoreUriVariables = ignoreUriVariables;
	}

	public void setIgnoreArguments(boolean ignoreArguments) {
		this.ignoreArguments = ignoreArguments;
	}

	public void setIgnoreExceptionStack(boolean ignoreExceptionStack) {
		this.ignoreExceptionStack = ignoreExceptionStack;
	}

	private boolean ignoreUriVariables;
	private boolean ignoreArguments;
	private boolean ignoreExceptionStack;

	/**
	 * Writes current {@link InvocationMetric} of the passed {@link InvocationEvent}
	 * object to log in info mode. Also put the entire conversation instance to
	 * {@link ThreadContext} with a key 'event'.
	 * <p>
	 * The corresponding log4j pattern layout recommended is:
	 * <code> pattern="%d [%t] %-5p %c-%X{event};%m%n" </code>
	 */
	@Override
	public void handleInvocationEvent(InvocationEvent event) {
		if (!ignoreUriVariables)
			event.setParameter(null);

		InvocationMetric metric = event.getCurrentMetric();
		if (ignoreArguments)
			metric.setArguments(null);

		if (event.getRootCause() == null)
			logger.info(event);
		else
			logger.info(event, extractRootCause(event.getRootCause()));
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
