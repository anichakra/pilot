package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.Arrays;

import me.anichakra.poc.pilot.framework.annotation.Event;

/**
 * The InvocationLineItem keeps all the information of a method call and is a
 * part of a {@link Invocation}.
 * 
 * @author anichakra
 * @see Invocation
 */
public class InvocationLineItem implements Cloneable {

	/**
	 * The separator for each attribute of this instance. This is used in the
	 * {@link #toString()} method.
	 */
	public static final String SEPARATOR = ";";

	private Object[] arguments;
	private long start;
	private long end;

	private InvocationStatus status;
	private Throwable rootCause;

	private Event event;

	private Object outcome;

	private String signature;

	private Layer layer;


	/**
	 * @return The method arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * Sets the method arguments
	 * 
	 * @param arguments
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	/**
	 * 
	 * @return The start timestamp of the invocation
	 */
	public long getStart() {
		return start;
	}

	/**
	 * Sets the start timestamp of the invocation
	 * 
	 * @param start
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * 
	 * @return The end timestamp of the invocation
	 */
	public long getEnd() {
		return end;
	}

	/**
	 * Sets the end timestamp of the invocation
	 * 
	 * @param end
	 */
	public void setEnd(long end) {
		this.end = end;
	}

	/**
	 * 
	 * @return The Status of the invocation
	 */
	public InvocationStatus getInvocationStatus() {
		return status;
	}

	/**
	 * Sets the status of the invocation
	 * 
	 * @param status
	 */
	public void setStatus(InvocationStatus status) {
		this.status = status;
	}

	/**
	 * Value deduced from start and end timestamp of the invocation
	 * 
	 * @return The duration of the invocation.
	 */
	public long getDuration() {
		return end == 0 ? 0 : (end - start);
	}

	/**
	 * Returns all the invocation attributes in the following order:
	 * <p>
	 * signature;arguments;status;duration
	 * <p>
	 * When status is F or C the duration is set.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(signature).append(SEPARATOR);

		if (getInvocationStatus().equals(InvocationStatus.Started)) {
			builder.append(SEPARATOR).append(getInvocationStatus());
			if (getArguments() != null) {
				String args = strip(getArguments());
				builder.append(SEPARATOR).append(args);
			}
		} else if (getInvocationStatus().equals(InvocationStatus.Completed)) {
			builder.append(SEPARATOR).append(getInvocationStatus());
			builder.append(SEPARATOR).append(getDuration());
			if (getOutcome() != null) {
				String outcome = "[" + getOutcome() + "]";
				builder.append(SEPARATOR).append(outcome);
			}
		} else {
			builder.append(SEPARATOR).append(getInvocationStatus());
			builder.append(SEPARATOR).append(getDuration());
			builder.append(SEPARATOR);

		}
		return builder.toString();
	}

	private String strip(Object[] arguments) {
		return Arrays.toString(arguments);
	}

	/**
	 * Sets the root cause for failed invocation
	 * 
	 * @param rootCause
	 */
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	/**
	 * @return The rootcause for failed invocation
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setOutcome(Object outcome) {
		this.outcome = outcome;
	}

	public Object getOutcome() {
		return outcome;
	}

	public void setSignature(String signature) {
		this.signature = signature;

	}

	public String getSignature() {
		return this.signature;
	}

	public void setLayer(Layer layer) {
		this.layer= layer;
	}
	
	public Layer getLayer() {
		return layer;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
