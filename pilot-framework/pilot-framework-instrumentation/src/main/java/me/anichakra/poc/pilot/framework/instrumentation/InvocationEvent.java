package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.Map;

public class InvocationEvent {

	private String eventId;
	private InvocationLineItem invocationLineItem;
	private Map<InvocationMetric, String> metrics;
	
	public InvocationEvent(String eventId, InvocationLineItem invocationLineItem, Map<InvocationMetric, String> metrics) {
		this.eventId = eventId;
		this.invocationLineItem = invocationLineItem;
		this.metrics = metrics;
	}

	public String getEventId() {
		return eventId;
	}

	public InvocationLineItem getInvocationLineItem() {
		return invocationLineItem;
	}

	public Map<InvocationMetric, String> getMetrics() {
		return metrics;
	}
}
