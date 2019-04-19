package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

import me.anichakra.poc.pilot.framework.util.CoreUtils;

/**
 * This processes each bean that contains either of {@link RestController},
 * {@link CommandService}, {@link QueryService}, {@link ApplicationService} or
 * {@link Repository} annotation and then search for any method that is
 * annotated as {@link Event}. It caches all those annotated methods with the
 * annotation instance.
 * 
 * @author anirbanchakraborty
 *
 */
@Component
public class EventAnnotationDetectionProcessor implements BeanPostProcessor {

	@Autowired
	BuildProperties buildProperties;

	private ConcurrentMap<String, Event> eventAnnotationCache = new ConcurrentHashMap<>();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		if (buildProperties != null && clazz.getName().startsWith(buildProperties.getGroup())
				&& !CoreUtils.isFramework(buildProperties.getGroup(), clazz)) {
			Annotation[] annotations = clazz.getAnnotations();
			Supplier<Stream<Annotation>> supplier = () -> Arrays.asList(annotations).stream();
			if (supplier.get().anyMatch(a -> a.annotationType().equals(CommandService.class)
					|| a.annotationType().equals(QueryService.class) || a.annotationType().equals(RestController.class)
					|| a.annotationType().equals(Repository.class))) { // event should be present in designated places

				eventAnnotationCache.putAll(Arrays.asList(clazz.getDeclaredMethods()).stream()
						.filter(m -> m.isAnnotationPresent(Event.class)).collect(Collectors
								.toConcurrentMap(m -> m.toString(), m -> m.getAnnotationsByType(Event.class)[0])));
			}
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * 
	 * @param signature The signature or toString() form of a {@link Method}
	 * @return The Event annotation instance from the event annotation cache.
	 */
	public Event getEventAnnotation(String signature) {
		return eventAnnotationCache.get(signature);
	}
}