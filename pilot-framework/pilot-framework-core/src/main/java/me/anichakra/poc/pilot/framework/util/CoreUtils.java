package me.anichakra.poc.pilot.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ClassUtils;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * Core utilties of the framework mainly doing class, field or method level of
 * introspection.
 * 
 * @author anirbanchakraborty
 *
 */
public class CoreUtils {

    private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}", ":", false);

    /**
     * Finds whether the bean is declared inside the framework. It finds that
     * checking the package of the bean. It checks if the package includes
     * "framework" in it.
     * 
     * @param pckg The package of the bean.
     * @param bean
     * @return
     */
    public static final boolean isFramework(String pckg, final Object bean) {
        String className = bean.getClass().getName();
        return className.startsWith(pckg) && className.contains(".framework.");
    }

    /**
     * Checks if the field in the bean is annotated with either of the annotations
     * passed.
     * 
     * @param bean            The bean instance
     * @param field           The field to introspect
     * @param annotationClass The varargs of annotation classes, if any of these
     *                        classes are annotated on that field.
     * @return
     */
    @SafeVarargs
    public static final boolean isFieldAnnotatedWithEither(final Object bean, final Field field,
            final Class<? extends Annotation>... annotationClass) {
        field.setAccessible(true);
        boolean flag = false;
        flag = Arrays.asList(annotationClass).stream().anyMatch(a -> {
            try {
                return field.getType().isAnnotationPresent(a)
                        || ClassUtils.getUserClass(field.get(bean).getClass()).isAnnotationPresent(a)
                        || AopUtils.getTargetClass(field.get(bean)).isAnnotationPresent(a);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return false;
            }

        });

        return flag;
    }

    /**
     * Resolves a template to retrieve a message from it by passing the context
     * object
     * 
     * @param template The template that can contain more than on variable or
     *                 placeholder. E.g. <code>Vehicle ${id} is not found</code>.
     *                 where id is the placeholder that should be one of the
     *                 properties of the context object.
     * @param context  The context object whose one or two properties would be used
     *                 as placeholder in the template
     * @return The resolved string that replaces all the values of the properties of
     *         the context object with the template placeholder.
     */
    public static String resolveProperty(String template, Object context) {
        Field[] fields = context.getClass().getDeclaredFields();
        Properties p = new Properties();
        BeanWrapper bw = new BeanWrapperImpl(context);
        for (Field field : fields) {
            if (bw.isReadableProperty(field.getName())) {
                p.put(field.getName(), String.valueOf(bw.getPropertyValue(field.getName())));
            }
        }
        return resolveValues(template, p);
    }

    /**
     * Resolves a template to retrieve a message from it by passing a set of
     * properties.
     * 
     * @param template The template that can contain more than on variable or
     *                 placeholder. E.g.
     *                 <code>Manufacturer name must be between ${min} and ${max}</code>,
     *                 where min and max should be part of the properties.
     * @param p The properties instance
     * @return The resolved string that replaces all the values of the properties of
     *
     */
    public static String resolveValues(String template, Properties p) {
        try {
            if (template.contains("${"))
                return helper.replacePlaceholders(template, p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }

}
