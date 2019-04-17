package me.anichakra.poc.pilot.framework.instrumentation.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.annotation.EventObject;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric.Status;

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
@ConfigurationProperties(prefix = "instrumentation.handlers.aws-sns")
public class AwsSnsPublishingEventHandler extends AbstractInvocationEventHandler {
	public static final String LOGGER_NAME = "INSTRUMENTATION_AWSSNS";
	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);

//	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);
	// private final NotificationMessagingTemplate notificationMessagingTemplate;
//	@Autowired
//	public AwsSnsPublishingEventHandler(AmazonSNS amazonSns) {
//		this.notificationMessagingTemplate = new NotificationMessagingTemplate(amazonSns);
//	}

//	public void send(String subject, String message) {
//		this.notificationMessagingTemplate.sendNotification("physicalTopicName", message, subject);
//	}

	@Override
	public void handleInvocationEvent(InvocationEvent invocationEvent) {
		Event event = invocationEvent.getCurrentMetric().getEvent();
		InvocationMetric metric = invocationEvent.getCurrentMetric();
		if (match(event)
				&& metric.getStatus().equals(Status.C)) {
			if(event.object().equals(EventObject.REQUEST)) {
			logger.info(metric.getArguments());
			} else {
				logger.info(metric.getOutcome());

			}
		}
		// send("subject", event.getCurrentMetric().getArguments().toString());
	}

	/**
	 * Clears the stack from {@link ThreadContext}
	 */
	public void clear() {
		ThreadContext.clearStack();
	}

}
