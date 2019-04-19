package me.anichakra.poc.pilot.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;

/**
 * Core utilties of the framework mainly doing class, field or method level of
 * introspection.
 * 
 * @author anirbanchakraborty
 *
 */
public class CoreUtils {

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

}
