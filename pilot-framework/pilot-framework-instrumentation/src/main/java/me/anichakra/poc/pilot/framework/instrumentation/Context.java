package me.anichakra.poc.pilot.framework.instrumentation;

/**
 * This is an enumeration of all the elements in the context of an invocation event. 
 * 
 * @author anichakra
 *
 */
public enum Context {
    /**
     * The source address from which this event is triggered
     */
    SOURCE,
    
    /**
     * The target application or code to which this request is made. Event generation starts from target.
     */
    TARGET,
    
    /**
     * The Path of the invocation start point or URI in case of web related request
     */
    PATH,

    /**
     * The parameters of a method call
     */
    PARAMETER,

    /**
     * The user who generated event
     */
    USER,

    /**
     * The conversation id that is generated when an event is starting. This conversation id is
     * passed to another application or system and there it becomes the CORRELATION id.
     */
    SESSION_ID,

    /**
     * The entire event data containing many event related information
     */
    EVENT,

    /**
     * The correlation for tracking the conversation origination.
     */
    CORRELATION;
}
