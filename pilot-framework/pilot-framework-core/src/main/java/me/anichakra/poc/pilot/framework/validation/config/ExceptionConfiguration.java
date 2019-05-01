package me.anichakra.poc.pilot.framework.validation.config;

import org.springframework.http.HttpStatus;

/**
 * Contains the structure of configuration for an exception with its message template and http status code.
 * @author anirbanchakraborty
 *
 */
public class ExceptionConfiguration {
    private String name;
    private String code;
    private HttpStatus httpStatus;
    private String template;

    /**
     * 
     */
    public ExceptionConfiguration() {

    }

    /**
     * 
     * @param name
     * @param code
     * @param httpStatus
     * @param template
     */
    public ExceptionConfiguration(String name, String code, HttpStatus httpStatus, String template) {
        super();
        this.name = name;
        this.code = code;
        this.httpStatus = httpStatus;
        this.template = template;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the httpStatus
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus the httpStatus to set
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }
}
