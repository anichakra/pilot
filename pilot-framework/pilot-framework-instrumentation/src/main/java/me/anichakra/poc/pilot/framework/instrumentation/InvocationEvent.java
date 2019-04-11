package me.anichakra.poc.pilot.framework.instrumentation;

import static me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric.SEPARATOR;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric.Status;

/**
 * InvocationEvent is a piece of information about an invocation that stays in
 * the execution from its start till complete or fail. An execution starts from
 * users request for a resource or information from an application and ends till
 * the application provides the resource back to the user. One user event or
 * request creates a invocation which is persisted in {@link ThreadLocal}.
 * <p>
 * The invocation relates to a particular execution or thread with multiple
 * {@link InvocationMetric} involved in it. Each Invocation instance maintains a
 * stack of Invocation. Each invocation can be considered to be a method call.
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
    private String localAddress = "";
    private String remoteAddress = "";
    private String id;
    private String eventId;
    private String path = "";
    private String user = "";
    private String correlationId = "";
    private boolean markExceptionInExecution;
    private Throwable lastRootCause = null;
    private String parameter;

    private Deque<InvocationMetric> invocationStack = new ArrayDeque<>();
    private Set<String> ignoreSignature = new HashSet<>();
    private static final ThreadLocal<InvocationEvent> CURRENT = new ThreadLocal<>();

    /**
     * Creates a Invocation with an id, event id, path and user. If Id is sent as
     * null a random id is created and assigned to the invocation.
     * 
     * @param id
     * @param eventId
     * @param path
     * @param user
     */
    InvocationEvent(String id, String eventId, String path, String user) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.eventId = eventId;
        this.path = path;
        this.user = user;
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
     * {@link InvocationMetric}s.
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
     * 
     * @return The correlation id if its present
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the correlation id to the invocation. The correlation id is the
     * invocation id of a parent invocation. For e.g if a web application is
     * executing a invocation which invokes a ReST call to another application, then
     * the invocation id is passed in the ReST call, so that the new invocation that
     * is starting in the second application exposing ReST service will send the
     * invocation id as correlation id to its Invocation instance.
     * 
     * @param correlationId
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Mark this Invocation instance as ignore. Hence no further invocation will be
     * stacked.
     */
    public void markIgnore() {
        InvocationMetric invocation = getCurrentMetric();
        ignoreSignature.add(invocation.getSignature());
    }

    /**
     * 
     * @return true if the invocation is already marked to be ignored.
     */
    public boolean isAlreadyMarkedIgnore() {
        InvocationMetric invocation = getCurrentMetric();
        if (invocation == null)
            return false;
        return ignoreSignature.contains(invocation.getSignature());
    }

    /**
     * Mark the {@link InvocationMetric} in a invocation to ignore if execution
     * duration of the invocation is less than specified. This is to ignore stacking
     * insignificant invocations which are repetitive which are not important for
     * profiling.
     * 
     * @param durationToIgnore
     */
    public void markIgnore(long durationToIgnore) {
        InvocationEvent event = InvocationEvent.getCurrent();
        InvocationMetric invocation = event.getCurrentMetric();
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
     * Get the current {@link InvocationMetric} instance
     * 
     * @return
     */
    public InvocationMetric getCurrentMetric() {
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
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(SEPARATOR).append(remoteAddress).append(SEPARATOR).append(localAddress)
                .append(SEPARATOR).append(getEventId()).append(SEPARATOR).append(getCorrelationId()).append(SEPARATOR)
                .append(user).append(SEPARATOR).append(path).append(SEPARATOR).append(parameter).append(SEPARATOR)
                .append(getLevel()).append(SEPARATOR)
                .append(getCurrentMetric());

        return builder.toString();
    }

    /**
     * This method creates an {@link InvocationMetric} object and set the class and
     * method signature including method parameters to it. It pushes the invocation
     * event instance to the stack.
     * 
     * @param signature
     * @param args
     */
    public void start(String signature, Object[] args) {
        InvocationMetric invocation = new InvocationMetric();
        invocation.setSignature(signature);
        if (args != null) {
            invocation.setArguments(Arrays.asList(args));
        }
        invocation.setStart(System.currentTimeMillis());
        invocation.setStatus(Status.S);
        invocationStack.push(invocation);
    }

    /**
     * Gets the current {@link InvocationMetric} instance and mark it as failed.
     */
    public void fail() {
        InvocationMetric invocation = this.getCurrentMetric();
        invocation.setEnd(System.currentTimeMillis());
        invocation.setStatus(Status.F);
        if (!this.isMarkExceptionInExecution()) {
            this.setMarkExceptionInExecution(true);
        }
    }

    /**
     * Gets the current {@link InvocationMetric} instance and mark it as completed.
     */
    public void complete() {
        InvocationEvent invocation = InvocationEvent.getCurrent();
        InvocationMetric invocationMetric = invocation.getCurrentMetric();
        invocationMetric.setEnd(System.currentTimeMillis());
        invocationMetric.setStatus(Status.C);
        invocation.setMarkExceptionInExecution(false);
    }

    /**
     * 
     * @return The event id
     */
    public String getEventId() {
        return this.eventId;
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
        InvocationMetric invocationMetric = invocation.getCurrentMetric();
        return invocationMetric.getRootCause();
    }

    /**
     * Sets the root cause if the invocation has failed due to some exception.
     * 
     * @param rootCause
     */
    public void setRootCause(Throwable rootCause) {
        InvocationEvent invocation = InvocationEvent.getCurrent();
        InvocationMetric invocationMetric = invocation.getCurrentMetric();
        if (lastRootCause != null && lastRootCause.equals(rootCause))
            return;
        invocationMetric.setRootCause(rootCause);
        lastRootCause = rootCause;
    }

    /**
     * Sets the parameter to the invocation
     * 
     * @param parameter
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * Sets the local address to the invocation
     * 
     * @param localAddress
     */
    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    /**
     * Sets the remote address to the invocation
     * 
     * @param remoteAddress
     */
    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getUser() {
        return user;
    }

    public String getParameter() {
        return parameter;
    }

}
