package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;



/**
 * This annotation is required to map one class to multiple class. It is
 * required only for the framework to relate identical classes.
 * 
 * @author anirbanchakraborty
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface ClassMapper {

    /**
     * All the classes to map with this class
     * 
     * @return
     */
    Class<?>[] classes();
}
