package me.anichakra.poc.pilot.framework.validation;

/**
 * Part of {@link ErrorInfo} that encapsulates the reason details for any
 * violation.
 * 
 * @author anirbanchakraborty
 *
 */
public class Reason {

    public Reason(Class<? extends Exception> rootCause, String source, String localizedMessage, Object invalidData) {
        super();
        this.setRootCause(rootCause);
        this.setSource(source);
        this.setInvalidData(invalidData);
        this.setLocalizedMessage(localizedMessage);
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the rootCause
     */
    public Class<? extends Exception> getRootCause() {
        return rootCause;
    }

    /**
     * @param rootCause the rootCause to set
     */
    public void setRootCause(Class<? extends Exception> rootCause) {
        this.rootCause = rootCause;
    }

    /**
     * @return the invalidData
     */
    public Object getInvalidData() {
        return invalidData;
    }

    /**
     * @param invalidData the invalidData to set
     */
    public void setInvalidData(Object invalidData) {
        this.invalidData = invalidData;
    }

    /**
     * @return the localizedMessage
     */
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    /**
     * @param localizedMessage the localizedMessage to set
     */
    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    private Class<? extends Exception> rootCause;
    private String source;
    private Object invalidData;
    private String localizedMessage;

}
