package me.anichakra.poc.pilot.framework.instrumentation;

/** 
 * A generic conversation handler that handles a conversation
 * @author anichakra
 *
 * @param <T>
 */
public interface InvocationEventHandler<T> {

    /**
     * Handle the invocation event to do something like logging, tracing, emailing, etc.
     * @param event
     */
    void handleInvocationEvent(InvocationEvent event);

    /**
     * Clear the event from the context. Do any clean up activity. After the invocation is ended this method is called.
     */
    void clear();

    /**
     * 
     * @return If this handler is enabled from configuration or not.
     */
    boolean isEnabled();

}
