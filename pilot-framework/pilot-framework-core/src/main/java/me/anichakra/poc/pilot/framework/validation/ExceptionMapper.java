package me.anichakra.poc.pilot.framework.validation;

import me.anichakra.poc.pilot.framework.annotation.ClassMapper;

/**
 * Maps the {@link ErrorInfo} to a custom bean so that it can be send as part of
 * the response when a violation happens. The implementation of this class
 * should annotated with {@link ClassMapper} with the exception classes for
 * which this mapping is required.
 * 
 * @author anirbanchakraborty
 * @see ClassMapper
 * @param <T>
 */
public interface ExceptionMapper<T> {

    /**
     * Transforms the ErrorInfo to a bean.
     * 
     * @param errorInfo
     * @return
     */
    T map(ErrorInfo errorInfo);
}
