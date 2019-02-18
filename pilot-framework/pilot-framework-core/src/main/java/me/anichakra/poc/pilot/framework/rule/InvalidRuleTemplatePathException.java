package me.anichakra.poc.pilot.framework.rule;

public class InvalidRuleTemplatePathException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidRuleTemplatePathException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
