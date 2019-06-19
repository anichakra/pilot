package me.anichakra.poc.pilot.framework.instrumentation.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.instrumentation.AbstractInvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBuilder;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationLineItem;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationStatus;

/**
 * This event handler writes the log to any tracing service, like Splunk,
 * Zipkin, CloudWatch etc. The metric information is send as a structured JSON
 * message.
 * <p>
 * 
 * @see InvocationEventBuilder
 * @see InvocationEventBus
 * @author anichakra
 *
 */
@Component
@ConfigurationProperties(prefix = "instrumentation.handlers.tracing")
public class TracingEventHandler extends AbstractInvocationEventHandler {
	public static final String LOGGER_NAME = "INSTRUMENTATION_TRACING";
	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);
	private List<InvocationMetric> metrics;

	public List<InvocationMetric> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<InvocationMetric> metrics) {
		this.metrics = metrics;
	}

	/**
	 * Writes current {@link InvocationLineItem} of the passed
	 * {@link InvocationEventBuilder} object to log in info mode. Also put the
	 * entire conversation instance to {@link ThreadContext} with a key 'event'.
	 * <p>
	 * The corresponding log4j pattern layout recommended is:
	 * <code> pattern="%d [%t] %-5p %c-%X{event};%m%n" </code>
	 */
	@Override
	public void handleInvocationEvent(InvocationEvent invocationEvent) {
		Event event = invocationEvent.getInvocationLineItem().getEvent();
		boolean matchFlag = match(event);
		if (!matchFlag)
			return;
		Map<String, Object> map = new LinkedHashMap<>();
		InvocationLineItem metric = invocationEvent.getInvocationLineItem();
		map.put("eventId", invocationEvent.getEventId());
		Map<String, Object> metricMap = new LinkedHashMap<>();

		map.put("metrics", metricMap);

		invocationEvent.getMetrics().entrySet().stream().filter(e -> metrics.contains(e.getKey()))
				.forEach(e -> metricMap.put(e.getKey().toString(), e.getValue()));
		map.put("signature", metric.getSignature());

		switch (event.object()) {
		case REQUEST:
			if (metric.getInvocationStatus().equals(InvocationStatus.Started)) {
				map.put("request", metric.getArguments());
				jsonify(map);
			}
			break;
		case RESPONSE:
			if (metric.getInvocationStatus().equals(InvocationStatus.Completed)
					|| metric.getInvocationStatus().equals(InvocationStatus.Failed)) {
				map.put("duration", metric.getDuration());
				map.put("request", metric.getArguments());
				map.put("response", metric.getOutcome());
				Optional.ofNullable(metric.getRootCause())
						.ifPresent(e -> map.put("error", NestedExceptionUtils.getRootCause(e)));
				jsonify(map);
			}
			break;
		}

	}

	private void jsonify(Map<String, Object> metric) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		try {
			logger.info(mapper.writeValueAsString(metric));
		} catch (JsonProcessingException e) {
			logger.error("Exception in logging invocation metrics", e);
		}
	}

	/**
	 * Clears the stack from {@link ThreadContext}
	 */
	public void clear() {
		ThreadContext.clearStack();
	}

}
