package me.anichakra.poc.pilot.framework.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * Simple utility class for String.
 * 
 * @author anirbanchakraborty
 *
 */
public class StringUtils {
	public static final String VALUE_SEPARATOR = ":";
	public static final String PREFIX = "${";
	public static final String SUFFIX = "}";

	static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(PREFIX, SUFFIX, VALUE_SEPARATOR, false);

	public static String format(String template, Object obj) throws IntrospectionException {
		if (template.contains(PREFIX) && template.contains("}")) {
			Properties p = new Properties();
			BeanWrapper bw = new BeanWrapperImpl(obj);
			PropertyDescriptor[] descriptors = bw.getPropertyDescriptors();

			for (PropertyDescriptor descriptor : descriptors) {
				String name = descriptor.getName();
				Object value = bw.getPropertyValue(name);
				Optional.ofNullable(value).ifPresent(c -> p.put(name, String.valueOf(c)));
			}
			return helper.replacePlaceholders(template, p);
		} else {
			return template;
		}
	}
}
