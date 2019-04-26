package me.anichakra.poc.pilot.framework.instrumentation;

import static me.anichakra.poc.pilot.framework.instrumentation.InvocationLineItem.SEPARATOR;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * InvocationEvent is a piece of information about an invocation that stays in
 * the execution from its start till complete or fail. An execution starts from
 * users request for a resource or information from an application and ends till
 * the application provides the resource back to the user. One user event or
 * request creates a invocation which is persisted in {@link ThreadLocal}.
 * <p>
 * The invocation relates to a particular execution or thread with multiple
 * {@link InvocationLineItem} involved in it. Each Invocation instance maintains
 * a stack of Invocation. Each invocation can be considered to be a method call.
 * For e.g. a invocation starts from a method a() which in turn calls another
 * method b() which in turns call third method c(). So the Invocation instance
 * stacks a() as Invocation, followed by b() and c() when individual method is
 * invoked. When the methods are completed or failed, the Invocation is popped
 * from the stack. So a() is pushed in stack before b() followed by c() but c()
 * is popped before b() which is popped before a().
 * <p>
 * Following are the important information stored in the Invocation apart from
 * the stack of invocation:
 * <ul>
 * <li>Local Address of the application</li>
 * <li>Remote Address of the user</li>
 * <li>Invocation id</li>
 * <li>Event Id</li>
 * <li>User Name who triggered the invocation</li>
 * <li>Path or URL of the request made</li>
 * <li>Some input parameters</li>
 * 
 * </ul>
 * 
 * @author anichakra
 *
 */
public class InvocationEvent {

	Map<InvocationMetric, String> metricMap = new EnumMap<>(InvocationMetric.class);
	private String id;
	private boolean markExceptionInExecution;
	private Throwable lastRootCause = null;
	private Deque<InvocationLineItem> invocationStack = new ArrayDeque<>();
	private Set<String> ignoreSignature = new HashSet<>();
	private static final ThreadLocal<InvocationEvent> CURRENT = new ThreadLocal<>();

	/**
	 * Creates a Invocation with a eventId.
	 * 
	 * @param id
	 */
	InvocationEvent(String id) {
		this.id = id;
	}

	/**
	 * Add metric to this Invocation instance
	 * 
	 * @param metric
	 * @param value
	 * @return
	 */
	public void addMetric(InvocationMetric metric, String value) {
		metricMap.put(metric, value);
	}

	/**
	 * Return the current Invocation instance residing in {@link ThreadLocal}
	 * 
	 * @return
	 */
	public synchronized static InvocationEvent getCurrent() {
		return CURRENT.get();
	}

	/**
	 * Set the Invocation if not present to the {@link ThreadLocal}
	 * 
	 * @param current
	 */
	static synchronized void setCurrent(InvocationEvent current) {
		if (getCurrent() == null)
			CURRENT.set(current);
	}

	/**
	 * Removes the Invocation from the current {@link ThreadLocal}
	 */
	static synchronized void clearCurrent() {
		CURRENT.remove();
	}

	/**
	 * Marks this invocation that an exception has occurred in one of the
	 * {@link InvocationLineItem}s.
	 * 
	 * @param markExceptionInExecution
	 */
	public void setMarkExceptionInExecution(boolean markExceptionInExecution) {
		this.markExceptionInExecution = markExceptionInExecution;
	}

	/**
	 * 
	 * @return true if this invocation is marked as failed.
	 */
	public boolean isMarkExceptionInExecution() {
		return markExceptionInExecution;
	}

	/**
	 * Mark this Invocation instance as ignore. Hence no further invocation will be
	 * stacked.
	 */
	public void markIgnore() {
		InvocationLineItem invocationLineItem = getCurrentLineItem();
		ignoreSignature.add(invocationLineItem.getSignature());
	}

	

	/**
	 * 
	 * @return true if the invocation is already marked to be ignored.
	 */
	public boolean isAlreadyMarkedIgnore() {
		InvocationLineItem invocationLineItem = getCurrentLineItem();
		if (invocationLineItem == null)
			return false;
		return ignoreSignature.contains(invocationLineItem.getSignature());
	}

	/**
	 * Mark the {@link InvocationLineItem} in a invocation to ignore if execution
	 * duration of the invocation is less than specified. This is to ignore stacking
	 * insignificant invocations which are repetitive which are not important for
	 * profiling.
	 * 
	 * @param durationToIgnore
	 */
	public void markIgnore(long durationToIgnore) {
		InvocationEvent event = InvocationEvent.getCurrent();
		InvocationLineItem invocation = event.getCurrentLineItem();
		if (invocation.getDuration() < durationToIgnore) {
			event.markIgnore();
		}
	}

	/**
	 * Pops the last invocation from stack
	 */
	public void removeLastInvocation() {
		invocationStack.pop();
	}

	/**
	 * Get the current {@link InvocationLineItem} instance
	 * 
	 * @return
	 */
	public InvocationLineItem getCurrentLineItem() {
		return invocationStack.getFirst();
	}

	/**
	 * Returns the String representation of the InvocationEvent instance in a single
	 * line. Following are returned in sequence separated by Invocation.SEPARATOR.
	 * <p>
	 * id;remoteAddress;localAddress;eventId;correlationId;user;path;parameter;level
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id).append(SEPARATOR);
		metricMap.entrySet().forEach(e->sb.append(e.getValue()).append(SEPARATOR));
		sb.append(getCurrentLineItem());
		return sb.toString();
	}

	/**
	 * This method creates an {@link InvocationLineItem} object and set the class
	 * and method signature including method parameters to it. It pushes the
	 * invocation event instance to the stack.
	 * 
	 * @param arguments
	 * 
	 * @param signature
	 * @param args
	 */
	public void start(String signature, Object[] arguments) {
		InvocationLineItem invocationLineItem = new InvocationLineItem();
		invocationLineItem.setSignature(signature);
		if (arguments != null) {
			invocationLineItem.setArguments(arguments);
		}
		invocationLineItem.setStart(System.currentTimeMillis());
		invocationLineItem.setStatus(InvocationStatus.Started);
		invocationStack.push(invocationLineItem);
	}

	/**
	 * Gets the current {@link InvocationLineItem} instance and mark it as failed.
	 */
	public void fail() {
		InvocationLineItem invocationLineItem = this.getCurrentLineItem();
		invocationLineItem.setEnd(System.currentTimeMillis());
		invocationLineItem.setStatus(InvocationStatus.Failed);
		if (!this.isMarkExceptionInExecution()) {
			this.setMarkExceptionInExecution(true);
		}
	}

	/**
	 * Gets the current {@link InvocationLineItem} instance and mark it as
	 * completed.
	 * 
	 * @param outcome
	 */
	public void complete(Object outcome) {
		InvocationEvent invocationEvent = InvocationEvent.getCurrent();
		InvocationLineItem invocationLineItem = invocationEvent.getCurrentLineItem();
		invocationLineItem.setEnd(System.currentTimeMillis());
		invocationLineItem.setOutcome(outcome);
		invocationLineItem.setStatus(InvocationStatus.Completed);
		invocationEvent.setMarkExceptionInExecution(false);
	}

	/**
	 * 
	 * @return The event id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * This returns the nested level of an invocation inside a invocation event.
	 * E.g. method a() level is 1, b() level is 2 and c() is 3.
	 * 
	 * @return
	 */
	public int getLevel() {
		return invocationStack.size() - 1;
	}

	/**
	 * If the invocation is failed, the root cause of the failure is captured
	 * 
	 * @return The root cause of the failure.
	 */
	public Throwable getRootCause() {
		InvocationEvent invocation = InvocationEvent.getCurrent();
		InvocationLineItem invocationMetric = invocation.getCurrentLineItem();
		return invocationMetric.getRootCause();
	}

	/**
	 * Sets the root cause if the invocation has failed due to some exception.
	 * 
	 * @param rootCause
	 */
	public void setRootCause(Throwable rootCause) {
		InvocationEvent invocation = InvocationEvent.getCurrent();
		InvocationLineItem invocationMetric = invocation.getCurrentLineItem();
		if (lastRootCause != null && lastRootCause.equals(rootCause))
			return;
		invocationMetric.setRootCause(rootCause);
		lastRootCause = rootCause;
	}
	
	public Map<InvocationMetric, String> getMetricMap() {
		return metricMap;
	}

}
