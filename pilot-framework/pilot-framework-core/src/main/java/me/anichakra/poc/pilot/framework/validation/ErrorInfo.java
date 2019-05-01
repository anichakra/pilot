package me.anichakra.poc.pilot.framework.validation;

import java.time.LocalDateTime;

/**
 * The ErrorInfo contains a list of attributes that is captured during a
 * violation or exception or exception handling.
 * 
 * @author anirbanchakraborty
 *
 */
public class ErrorInfo {
    private LocalDateTime timestamp;
    private String code;
    private String message;
    private String path;
    private org.springframework.http.HttpStatus httpStatus;
    private Reason reason;

    /**
     * Constructs an ErrorInfo from timestamp, {@link HttpStatus} and exception source path.
     * @param timestamp
     * @param httpStatus
     * @param path
     */
    public ErrorInfo(LocalDateTime timestamp, org.springframework.http.HttpStatus httpStatus, String path) {
        super();
        this.setPath(path);
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
    }

    /**
     * 
     * @return The local timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the local timestamp
     * @param timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    
    public HttpStatus getHttpStatus() {
        return new HttpStatus(httpStatus.value(), httpStatus.name());
    }

    public static class HttpStatus {
        private String name;
        private int value;

        public HttpStatus(int value, String name) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

}
