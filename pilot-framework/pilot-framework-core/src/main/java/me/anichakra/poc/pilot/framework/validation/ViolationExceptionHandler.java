package me.anichakra.poc.pilot.framework.validation;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import me.anichakra.poc.pilot.framework.annotation.ClassMapper;
import me.anichakra.poc.pilot.framework.annotation.processor.ClassMapperAnnotationProcessor;
import me.anichakra.poc.pilot.framework.annotation.processor.InvalidAnnotationException;
import me.anichakra.poc.pilot.framework.util.CoreUtils;
import me.anichakra.poc.pilot.framework.validation.config.ExceptionConfiguration;
import me.anichakra.poc.pilot.framework.validation.config.ValidationConfiguration;

/**
 * This centralized exception handler handles all exceptions raised from bottom
 * layers and convert the same into {@link ErrorInfo} and then mapped the same
 * into custom response using {@link ExceptionMapper} if present in the
 * classpath and annotated with {@link ClassMapper}.
 * 
 * @author anirbanchakraborty
 * @see ExceptionConfiguration
 */
@ControllerAdvice
@Component
public class ViolationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INVALID_VALUE = "invalidValue";
    @Autowired
    private ValidationConfiguration config;

    @Autowired
    private ClassMapperAnnotationProcessor classMapperAnnotationProcessor;

    /**
     * Handles any exception and finds the configured templates for the exact
     * exception type. If not found, it tries to map its super classes with any
     * message template configuration.
     * 
     * @see ExceptionConfiguration
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericException(Exception ex, HttpServletRequest request) {
        String name = ex.getClass().getSimpleName();
        ExceptionConfiguration exceptionConfig = config.getExceptionConfig(name);

        exceptionConfig = Optional.ofNullable(exceptionConfig)
                .orElse(config.getExceptionConfig(ex.getClass().getName()));

        exceptionConfig = Optional.ofNullable(exceptionConfig).orElse(findMatch(ex.getClass(), exceptionConfig));

        ErrorInfo errorInfo = null;
        HttpStatus httpStatus = null;
        Object invalidData = null;
        httpStatus = exceptionConfig.getHttpStatus();
        String message = CoreUtils.resolveProperty(exceptionConfig.getTemplate(), ex);
        errorInfo = new ErrorInfo(LocalDateTime.now(), exceptionConfig.getHttpStatus(),
                new ServletServerHttpRequest(request).getURI().toString());
        BeanWrapper bw = new BeanWrapperImpl(ex);
        Supplier<Stream<Object>> supplier = () -> Arrays.asList(ex.getClass().getDeclaredFields()).stream()
                .filter(f -> f.isAnnotationPresent(InvalidValue.class) && bw.isReadableProperty(f.getName()))
                .map(f -> bw.getPropertyValue(f.getName()));
        if (supplier.get().count() == 1) {
            invalidData = supplier.get().findFirst();
        } else {
            invalidData = supplier.get().collect(Collectors.toList()).toArray();
        }
        errorInfo.setCode(exceptionConfig.getCode());
        errorInfo.setMessage(message);

        errorInfo.setReason(
                new Reason(ex.getClass(), ex.getStackTrace()[0].getClassName(), ex.getLocalizedMessage(), invalidData));
        return new ResponseEntity<>(map(errorInfo), httpStatus);

    }

    private ExceptionConfiguration findMatch(Class<?> exceptionClass, ExceptionConfiguration exceptionConfig) {
        if (exceptionClass.equals(Exception.class)) {
            String n = exceptionClass.getName();
            ExceptionConfiguration e = config.getExceptionConfig(n);
            if (e == null) {
                return new ExceptionConfiguration(Exception.class.getName(), "exception.unknown",
                        HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Server Exception");
            } else
                return e;
        }
        String name = exceptionClass.getName();
        ExceptionConfiguration ecnew = config.getExceptionConfig(name);
        if (ecnew == null)
            return findMatch(exceptionClass.getSuperclass(), ecnew);
        return null;

    }

    /**
     * Handles all JSR303 validation exceptions.
     */
    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now(), status,
                ((ServletWebRequest) request).getRequest().getRequestURL().toString());

        ex.getBindingResult().getFieldErrors().forEach(t -> {
            String code = t.getDefaultMessage();

            String template = config.getErrorTemplate(code); // if code is mentioned using message

            if (template == null) {
                code = t.getObjectName() + "." + t.getField() + "." + t.getCode();
                template = config.getErrorTemplate(code); // if still
                                                          // template is
                                                          // not found
                                                          // then try with
                                                          // domain.attribute.annotation_simple_name
            }
            String message = null;
            if (template == null) {
                code = null;
                message = t.getDefaultMessage();
            } else {
                BeanPropertyBindingResult br = (BeanPropertyBindingResult) ex.getBindingResult();
                Field field;
                try {
                    field = br.getTarget().getClass().getDeclaredField(t.getField());
                } catch (NoSuchFieldException | SecurityException e1) {
                    throw new InvalidAnnotationException("Invalid field found", br);

                }
                Annotation annotation = Arrays.asList(field.getAnnotations()).stream()
                        .filter(a -> a.annotationType().getSimpleName().equals(t.getCode())).findAny().get();
                Properties p = new Properties();
                p.put(INVALID_VALUE, t.getRejectedValue());
                Arrays.asList(annotation.annotationType().getDeclaredMethods()).stream().forEach(m -> {
                    try {
                        p.put(m.getName(), String.valueOf(m.invoke(annotation)));
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        throw new InvalidAnnotationException(e, br);
                    }
                });

                message = CoreUtils.resolveValues(template, p);

            }
            errorInfo.setCode(code);
            errorInfo.setMessage(message);
            errorInfo.setReason(new Reason(ex.getClass(), ex.getStackTrace()[0].getClassName(),
                    ex.getLocalizedMessage(), t.getRejectedValue()));

        });
        return new ResponseEntity<>(map(errorInfo), headers, status);
    }

    /**
     * Handles all {@link ConstraintViolationException} raised because of URI data
     * validation failed.
     * 
     * @param request
     * @param ex
     * @return
     * @throws IOException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(HttpServletRequest request,
            ConstraintViolationException ex) throws IOException {
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
                new ServletServerHttpRequest(request).getURI().toString());

        ex.getConstraintViolations().stream().forEach(t -> {
            String code = t.getMessage();

            String template = config.getErrorTemplate(code); // if code is mentioned using message
                                                             // attribute in annotation
            if (template == null) {
                code = t.getPropertyPath().toString();
                template = config.getErrorTemplate(code);

            }
            // code not mentioned in message attribute in annotation, try using property
            // path (i.e. methodName.attribute)

            if (template == null) {
                code = t.getPropertyPath().toString() + "."
                        + t.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
                template = config.getErrorTemplate(code); // if still
                                                          // template is
                                                          // not found
                                                          // then try with
                                                          // methodName.attribute.annotation_simple_name
            }

            String message = null;
            if (template == null) {
                code = null;
                message = t.getMessage();
            } else {
                Properties p = new Properties();
                t.getConstraintDescriptor().getAttributes().entrySet()
                        .forEach(e -> p.put(e.getKey(), String.valueOf(e.getValue())));
                p.put(INVALID_VALUE, t.getInvalidValue());
                message = CoreUtils.resolveValues(template, p);
            }
            errorInfo.setCode(code);
            errorInfo.setMessage(message);
            errorInfo.setReason(new Reason(ex.getClass(), ex.getStackTrace()[0].getClassName(),
                    ex.getLocalizedMessage(), t.getInvalidValue()));

        });

        return new ResponseEntity<>(map(errorInfo), HttpStatus.BAD_REQUEST);
    }

    private Object map(ErrorInfo errorInfo) {
        Class<?> exClass = errorInfo.getReason().getRootCause();
        ExceptionMapper<?> mapper = findExceptionMapper(errorInfo, exClass);

        if (mapper != null) {
            return mapper.map(errorInfo);
        } else {
            return errorInfo;
        }

    }

    private ExceptionMapper<?> findExceptionMapper(ErrorInfo errorInfo, Class<?> exClass) {
        ExceptionMapper<?> mapper = (ExceptionMapper<?>) classMapperAnnotationProcessor.getBeanForMappedClass(exClass);
        if (mapper == null && !exClass.equals(Exception.class)) {
            Class<?> ec = exClass.getSuperclass();
            mapper = findExceptionMapper(errorInfo, ec);
        }
        return mapper;
    }

}
