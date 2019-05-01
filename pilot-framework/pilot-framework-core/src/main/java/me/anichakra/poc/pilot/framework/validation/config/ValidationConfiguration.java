package me.anichakra.poc.pilot.framework.validation.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The main structure for message configuration. This contains two maps, the first one is a map of
 * validations that contains the templates for message pertining to JSR303, and
 * the second one a map of ExceptionConfiguration.
 * 
 * @author anirbanchakraborty
 *
 */
@Component
@ConfigurationProperties(prefix = "message")
public class ValidationConfiguration {
    private Map<String, String> validations = new HashMap<>(10);
    private Map<String, ExceptionConfiguration> exceptions = new HashMap<>(10);

    /**
     * 
     * @param validations
     */
    public void setValidations(Map<String, String> validations) {
        this.validations = validations;
    }

    /**
     * 
     * @param errorCode
     * @return
     */
    
    public String getErrorTemplate(String errorCode) {
        return validations.get(errorCode);
    }

    /**
     * @param exceptions the exceptions to set
     */
    public void setExceptions(List<ExceptionConfiguration> exceptionConfigs) {
        exceptionConfigs.stream().forEach(e -> exceptions.put(e.getName(), e));
    }

    /**
     * 
     * @param name
     * @return
     */
    public ExceptionConfiguration getExceptionConfig(String name) {
        return exceptions.get(name);
    }

}
