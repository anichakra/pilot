package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Component
public class EventAnnotationDetectionProcessor implements BeanPostProcessor {

	@Autowired
	BuildProperties buildProperties;

	private ConcurrentMap<String, Event[]> eventAnnotationCache = new ConcurrentHashMap<>();

	private boolean isFramework(final Class<?> clazz) {
		return clazz.getName().startsWith(buildProperties.getGroup()) && clazz.getName().contains(".framework.");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		if (buildProperties != null && clazz.getName().startsWith(buildProperties.getGroup()) && !isFramework(clazz)) {
			Annotation[] annotations = clazz.getAnnotations();
			Supplier<Stream<Annotation>> supplier = () -> Arrays.asList(annotations).stream();
			if (supplier.get().anyMatch(a -> a.annotationType().equals(CommandService.class)
					|| a.annotationType().equals(QueryService.class) || a.annotationType().equals(RestController.class)
					|| a.annotationType().equals(Repository.class))) { // event should be present in designated places
				eventAnnotationCache.putAll(Arrays.asList(clazz.getDeclaredMethods()).stream()
						.filter(m -> !m.isSynthetic() && m.isAnnotationPresent(Event.class))
						.collect(Collectors.toConcurrentMap(m -> clazz.getName() + "." + m.toString(),
								m -> m.getAnnotationsByType(Event.class))));

			}

		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	public Event getEventAnnotation(String signature)
	{
		return null;
	}
}