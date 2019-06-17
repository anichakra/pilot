package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.Optional;
import java.util.UUID;

import me.anichakra.poc.pilot.framework.annotation.Event;

/**
 * One object of Invocation can be considered to be one line of invocation that
 * starts from one layer and proceeds to the next layer till the invocation is
 * ended in the first layer. One invocation can contain multiple child
 * invocations, i.e. one layer can call other layers.
 * 
 * @author anichakra
 *
 */
public class Invocation {
	private InvocationEventBus invocationEventBus;

	private Event event;

	private Layer layer;

	private long durationToIgnore;

	public Invocation(Layer layer, InvocationEventBus invocationEventBus) {
		this.layer = layer;
		this.invocationEventBus = invocationEventBus;
		InvocationEvent invocationEvent = Optional.ofNullable(InvocationEvent.getCurrent())
				.orElse(new InvocationEvent(UUID.randomUUID().toString()));
		InvocationEvent.setCurrent(invocationEvent);
	}

	/**
	 * Starts an invocation with an array of parameters, for e.g. method parameters
	 * or arguments.
	 * 
	 * @param params Method parameters, HTTP request parameters, headers etc.
	 * @return
	 */
	public void start(String signature, Object[] parameters) {
		InvocationEvent invocationEvent = InvocationEvent.getCurrent();
//		if (invocationEvent.getLevel() < 0) {
//			invocationEventBus.clearAll();
//		}
		invocationEvent.start(signature, parameters);
		InvocationLineItem currentInvocationMetric = invocationEvent.getCurrentLineItem();
		Optional.ofNullable(currentInvocationMetric).ifPresent(c -> c.setEvent(event));
		Optional.ofNullable(currentInvocationMetric).ifPresent(c -> c.setLayer(layer));

		if (!invocationEvent.isAlreadyMarkedIgnore()) {
			invocationEventBus.fireInvocationEvent(invocationEvent);
		}
	}

	/**
	 * Ends an invocation. If the duration of the invocation is too small, not need
	 * to consider.
	 * 
	 * @param outcome
	 */
	public void end(Object outcome) {
		InvocationEvent invocationEvent = InvocationEvent.getCurrent();
		if (invocationEvent != null) {
			invocationEvent.complete(outcome);
			if (!invocationEvent.isAlreadyMarkedIgnore()) {
				invocationEventBus.fireInvocationEvent(invocationEvent);
			}
			invocationEvent.markIgnore(durationToIgnore);
			invocationEvent.removeLastInvocation();
			if (invocationEvent.getLevel() < 0) {
				invocationEventBus.clearAll();
			}
		}
	}

	/**
	 * When an invocation is failed, due to some exception, this invocation should
	 * be marked as failed with the root cause.
	 * 
	 * @param rootCause The actual exception instance for which the failure
	 *                  happened.
	 */
	public void failed(Throwable rootCause) {
		InvocationEvent invocationEvent = InvocationEvent.getCurrent();
		if (invocationEvent != null) {
			invocationEvent.fail();
			invocationEvent.setRootCause(rootCause);
			invocationEventBus.fireInvocationEvent(invocationEvent);
			invocationEvent.removeLastInvocation();
			if (invocationEvent.getLevel() < 0) {
				invocationEventBus.clearAll();
			}
		}

	}

	/**
	 * Recursive method that traverses through a {@link Throwable} and finds the
	 * leaf level root cause.
	 * 
	 * @param t
	 * @return The extracted root cause Throwable from the passed {@link Throwable}
	 *         instance.
	 */
	public static Throwable getRootCause(Throwable t) {
		Throwable rootCause = t.getCause();
		if (rootCause != null) {
			return getRootCause(rootCause);
		}
		return t;
	}

	public void setEventBus(InvocationEventBus eventBus) {
		this.invocationEventBus = eventBus;

	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Layer getLayer() {
		return layer;
	}

	public Invocation addMetric(InvocationMetric metric, String value) {
		InvocationEvent invocationEvent = InvocationEvent.getCurrent();
		invocationEvent.addMetric(metric, value);
		return this;
	}

	public void setDurationToIgnore(long ignoreDurationInMillis) {
		this.durationToIgnore = ignoreDurationInMillis;
	}

}