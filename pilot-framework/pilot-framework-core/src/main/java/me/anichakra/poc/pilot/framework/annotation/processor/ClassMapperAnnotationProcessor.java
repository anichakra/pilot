package me.anichakra.poc.pilot.framework.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.annotation.ClassMapper;
import me.anichakra.poc.pilot.framework.util.CoreUtils;

/**
 * This processes each bean that contains {@link ClassMapper} annotation and
 * cache the class and the annotation for further use by the framework.
 * 
 * @author anirbanchakraborty
 *
 */
@Component
public class ClassMapperAnnotationProcessor implements BeanPostProcessor {

    @Autowired
    BuildProperties buildProperties;

    private ConcurrentMap<Class<?>, ClassMapper> mappedClassesCache = new ConcurrentHashMap<>();
    private ConcurrentMap<Class<?>, Object> reverseClassMapCache = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (buildProperties != null && clazz.getName().startsWith(buildProperties.getGroup())
                && !CoreUtils.isFramework(buildProperties.getGroup(), clazz)) {
            Annotation[] annotations = clazz.getAnnotations();
            Arrays.asList(annotations).stream().filter(a -> a.annotationType().equals(ClassMapper.class)).findAny()
                    .ifPresent(a -> {
                        ClassMapper classMapperAnnotation = (ClassMapper) a;
                        mappedClassesCache.put(bean.getClass(), (ClassMapper) a);
                        Class<?>[] exClasses = classMapperAnnotation.classes();
                        Arrays.asList(exClasses).forEach(ex -> reverseClassMapCache.putIfAbsent(ex, bean));
                    });
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Returns the {@link ClassMapper} annotation instance associated with a class
     * 
     * @param clazz The class object to check
     * @return The {@link ClassMapper} annotation mapped with the class, or null if
     *         this class is not mapped.
     */
    public ClassMapper getClassMapperAnnotation(Class<?> clazz) {
        return mappedClassesCache.get(clazz);
    }

    /**
     * This does reverse look up on the mapped classes. It finds the object whose
     * class is mapped with this class.
     * 
     * @param clazz The class that is mapped to this object's class
     * @return The object whose class contains the {@link ClassMapper} annotation
     */
    public Object getBeanForMappedClass(Class<?> clazz) {
        return reverseClassMapCache.get(clazz);
    }

}