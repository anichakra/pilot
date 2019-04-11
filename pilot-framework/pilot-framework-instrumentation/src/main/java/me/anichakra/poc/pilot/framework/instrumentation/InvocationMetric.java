package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.List;

/**
 * The Invocation keeps all the information of a method call and is a part of a Conversation.
 * 
 * @author anichakra
 * @see InvocationEvent
 */
public class InvocationMetric {

    public enum Status {
        /**
         * Started
         */
        S,
        /**
         * Completed
         */
        C,
        /**
         * Failed
         */
        F;
    }

    /**
     * The separator for each attribute of this instance. This is used in the {@link #toString()}
     * method.
     */
    public static final String SEPARATOR = ";";

    private String signature;
    private List<Object> arguments;
    private long start;
    private long end;

    private Status status;
    private Throwable rootCause;

	private String[] eventNames;

    /**
     * @return The method arguments
     */
    public List<Object> getArguments() {
        return arguments;
    }

    /**
     * Sets the method arguments
     * 
     * @param arguments
     */
    public void setArguments(List<Object> arguments) {
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
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the invocation
     * 
     * @param status
     */
    public void setStatus(Status status) {
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
     * 
     * @return The signature of the method. This includes the fully qualified classname also.
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the signature of the method.
     * 
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
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
        StringBuilder builder = new StringBuilder().append(getSignature());
        String args = strip(getArguments());
        if (!getStatus().equals(Status.S)) {
            if (getArguments() != null)
                builder.append(SEPARATOR).append(args);
            builder.append(SEPARATOR).append(getStatus());
            builder.append(SEPARATOR).append(getDuration());
        } else {
            if (getArguments() != null)
                builder.append(SEPARATOR).append(args);
            builder.append(SEPARATOR).append(getStatus());
        }
        return builder.toString();
    }

    private String strip(List<Object> arguments) {
        if (arguments == null)
            return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (Object obj : arguments) {
        	if(obj!=null && !("".equals(obj.toString()))) {
        	     String s = obj.toString();
        	     sb.append(s.substring(0, s.length() > 100 ? 100 : s.length() - 1));
        	     sb.append(",");
        	}
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
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

	public void setEventNames(String[] eventNames) {
		this.eventNames = eventNames;
	}
	
	public String[] getEventNames() {
		return this.eventNames;
	}
}
