package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    private boolean fireEvent;
    private Map<Context, String> contextData;

    public Map<Context, String> getContextData() {
        return contextData;
    }

    private String signature;

    /**
     * Creates an invocation from a signature (class name and method name together)
     * 
     * @param signature
     */
    public Invocation(String signature) {
        this.signature = signature;
        invocationEventBus = new InvocationEventBus();
        this.contextData = new HashMap<Context, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String get(Object key) {
                if (this.containsKey(key)) {
                    return super.get(key);
                }

                if (key.equals(Context.EVENT)) {
                    InvocationEvent invocation = InvocationEvent.getCurrent();
                    if (invocation != null) {
                        return invocation.getEventId();
                    }
                }
                return null;
            }
        };
    }

    /**
     * Starts an invocation with an array of parameters, for e.g. method parameters
     * or arguments.
     * 
     * @param params Method parameters, HTTP request parameters, headers etc.
     * @return
     */
    public void start(Object[] params) {
        InvocationEvent invocationEvent = InvocationEvent.getCurrent();
        if (invocationEvent == null) {
            invocationEvent = createInvocationEvent();
        } else {
            if (invocationEvent.getLevel() < 0) {
                invocationEventBus.clearAll();
            }
        }
        invocationEvent.start(signature, params);
        if (!invocationEvent.isAlreadyMarkedIgnore() && fireEvent) {
            invocationEventBus.fireInvocationEvent(invocationEvent);
        }
    }

    /**
     * Ends an invocation. If the duration of the invocation is too small, not need
     * to consider.
     * 
     * @param durationToIgnore In millis
     */
    public void end(long durationToIgnore) {
        InvocationEvent invocationEvent = InvocationEvent.getCurrent();
        if (invocationEvent != null) {
            invocationEvent.complete();
            if (!invocationEvent.isAlreadyMarkedIgnore() && fireEvent) {
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
     * When an invocation is failed, due to some exception, this invocation should be marked as failed with the root cause.
     * @param rootCause The actual exception instance for which the failure happened.
     * @param rootCauseOnly If only to consider the root cause of the exception to be marked as the input to the event. If not then the entire stack trace of the exception will be noted.
     */
    public void failed(Throwable rootCause, boolean rootCauseOnly) {

        InvocationEvent invocationEvent = InvocationEvent.getCurrent();
        if (invocationEvent != null) {
            invocationEvent.fail();
            invocationEvent.setRootCause(rootCauseOnly ? getRootCause(rootCause) : rootCause);
            invocationEventBus.fireInvocationEvent(invocationEvent);
            invocationEvent.removeLastInvocation();
            if (invocationEvent.getLevel() < 0) {
                invocationEventBus.clearAll();
            }
        }

    }

    private InvocationEvent createInvocationEvent() {
        InvocationEvent invocationEvent;
        invocationEvent = new InvocationEvent((String) contextData.get(Context.CONVERSATION),
                UUID.randomUUID().toString(), (String) contextData.get(Context.PATH),
                (String) contextData.get(Context.USER));
        invocationEvent.setRemoteAddress((String) contextData.get(Context.SOURCE));
        invocationEvent.setLocalAddress((String) contextData.get(Context.TARGET));
        invocationEvent.setCorrelationId((String) contextData.get(Context.CORRELATION));
        InvocationEvent.setCurrent(invocationEvent);
        return invocationEvent;
    }

    public void setFireInvocationEvent(boolean fireEvent) {
        this.fireEvent = fireEvent;
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

}