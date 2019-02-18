package me.anichakra.poc.pilot.framework.instrumentation.log;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
@ConfigurationProperties(prefix = "framework.instrumentation.handlers.log")
public class LogInvocationEventHandler implements InvocationEventHandler<InvocationEvent> {
    public static final String LOGGER_NAME = "INSTRUMENTATION";
    private static final Logger logger = LogManager.getLogger(LOGGER_NAME);
    private boolean enabled;
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
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
        ThreadContext.put("event", event.toString());
        ThreadContext.put("eventId", event.getEventId());
        ThreadContext.put("correlationId", event.getCorrelationId());
        ThreadContext.put("transactionId", Optional.ofNullable(event.getCorrelationId()).orElse(event.getEventId()));
        if (event.getRootCause() == null)
            logger.info(event.getCurrentInvocation());
        else
            logger.info(event.getCurrentInvocation(), event.getRootCause());
    }

    /**
     * Clears the stack from {@link ThreadContext}
     */
    public void clear() {
        ThreadContext.clearStack();
    }

}
