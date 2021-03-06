package me.anichakra.poc.pilot.framework.instrumentation.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.annotation.EventObject;
import me.anichakra.poc.pilot.framework.instrumentation.AbstractInvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBuilder;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationLineItem;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationStatus;

/**
 * This {@link InvocationEventHandler} handles the {@link InvocationEventBuilder} and
 * checks the event contains an marked {@link Event} that is matched with the
 * configured event-names. If matched the InvocationEvent's method argument
 * object or method return object is published to configured SNS topic.
 * <p>
 * 
 * @see InvocationEventBuilder
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
		Event event = invocationEvent.getInvocationLineItem().getEvent();
		InvocationLineItem metric = invocationEvent.getInvocationLineItem();
		boolean matchFlag = match(event);
		if (matchFlag && metric.getInvocationStatus().equals(InvocationStatus.Started)
				&& event.object().equals(EventObject.REQUEST)) {
			logger.info(metric.getArguments());

		} else if (matchFlag && metric.getInvocationStatus().equals(InvocationStatus.Completed)
				&& event.object().equals(EventObject.RESPONSE)) {
			logger.info(metric.getOutcome());

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
