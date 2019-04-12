package me.anichakra.poc.pilot.framework.instrumentation.handler;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
	public static final String LOGGER_NAME = "INSTRUMENTATION";
//	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);
	//private final NotificationMessagingTemplate notificationMessagingTemplate;
//	@Autowired
//	public AwsSnsPublishingEventHandler(AmazonSNS amazonSns) {
//		this.notificationMessagingTemplate = new NotificationMessagingTemplate(amazonSns);
//	}

//	public void send(String subject, String message) {
//		this.notificationMessagingTemplate.sendNotification("physicalTopicName", message, subject);
//	}
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
		if (hasAnyEventMatched(event.getCurrentMetric().getEventNames())
				&& event.getCurrentMetric().getStatus().equals(Status.C))
			System.out.println("#######" + event);
		System.out.println("#######" + event.getCurrentMetric().getArguments());
	//	send("subject", event.getCurrentMetric().getArguments().toString());

	}

	/**
	 * Clears the stack from {@link ThreadContext}
	 */
	public void clear() {
		ThreadContext.clearStack();
	}
	 
	
}
