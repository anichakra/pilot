package me.anichakra.poc.pilot.framework.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * This bean post processor validates all the beans that are injected throughout
 * the application. It checks for proper implementation of CQRS pattern.
 * 
 * @author anirbanchakraborty
 *
 */
@Component
public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor {

	@Autowired
	BuildProperties buildProperties;
	private ConfigurableListableBeanFactory configurableBeanFactory;

	@Autowired
	public InjectAnnotationBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
		this.configurableBeanFactory = beanFactory;
		annotationValidationMap.put(RestController.class, restControllerValidation);
		annotationValidationMap.put(CommandService.class, commandServiceValidation);
		annotationValidationMap.put(QueryService.class, queryServiceValidation);
		annotationValidationMap.put(ApplicationService.class, applicationServiceValidation);
	}

	private Map<Class<? extends Annotation>, Consumer<Object>> annotationValidationMap = new HashMap<>();

	private Consumer<Object> restControllerValidation = b -> {
		Supplier<Stream<Field>> supplier = () -> Arrays.asList(b.getClass().getDeclaredFields()).stream();
		long count = supplier.get().filter(f -> f.isAnnotationPresent(Inject.class)).count();

		if (count != 1)
			throw new BeanPostProcessorValidationException("Controller class must have only one injectable field", b);

		final String message = "A Controller class can only inject one CommandService, or one QueryService or one ApplicationService";
		validateBean(b, supplier, message, CommandService.class, QueryService.class, ApplicationService.class);

	};

	private Consumer<Object> commandServiceValidation = b -> {
		Supplier<Stream<Field>> supplier = null;
		if (!b.getClass().getAnnotation(CommandService.class).stateful()) {
			supplier = validateInjection(b);
		}
		final String message = "A CommandService class can only inject another CommandService, an ApplicationService, a FrameworkService or a Repository";
		validateBean(b, supplier, message, CommandService.class, ApplicationService.class, FrameworkService.class,
				Repository.class);

	};

	private Consumer<Object> queryServiceValidation = b -> {
		Supplier<Stream<Field>> supplier = null;
		if (!b.getClass().getAnnotation(QueryService.class).stateful()) {
			supplier = validateInjection(b);
		}
		final String message = "A QueryService class can only inject another QueryService, an ApplicationService, a FrameworkService or a Repository";
		validateBean(b, supplier, message, QueryService.class, ApplicationService.class, FrameworkService.class,
				Repository.class);
	};

	private Consumer<Object> applicationServiceValidation = b -> {
		Supplier<Stream<Field>> supplier = null;
		if (!b.getClass().getAnnotation(ApplicationService.class).stateful()) {
			supplier = validateInjection(b);
		}
		final String message = "An ApplicationService class can only inject another ApplicationService or a FrameworkService";
		validateBean(b, supplier, message, ApplicationService.class, FrameworkService.class);
	};

	@SafeVarargs
	private final void validateBean(Object b, Supplier<Stream<Field>> supplier, String validationMessage,
			Class<? extends Annotation>... annotationClass) {
		if (!supplier.get().filter(f -> f.isAnnotationPresent(Inject.class))
				.allMatch(c -> isFieldAnnotatedWithEither(c, annotationClass)))
			throw new BeanPostProcessorValidationException(validationMessage, b);
	}

	@SafeVarargs
	private final boolean isFieldAnnotatedWithEither(final Field field,
			final Class<? extends Annotation>... annotationClass) {

		boolean flag = Arrays.asList(annotationClass).stream()
				.anyMatch(a -> field.getType().isAnnotationPresent(a)
						|| ClassUtils.getUserClass(configurableBeanFactory.getBean(field.getType()).getClass())
								.isAnnotationPresent(a) || isFramework(field.getType()));
		return flag;

	}

	private boolean isFramework(final Class<?> clazz) {
		return clazz.getName().startsWith(buildProperties.getGroup())
				&& clazz.getName().contains(".framework.");
	}

	private Supplier<Stream<Field>> validateInjection(Object b) {
		Supplier<Stream<Field>> supplier = () -> Arrays.asList(b.getClass().getDeclaredFields()).stream();
		if (!supplier.get().allMatch(f -> f.isAnnotationPresent(Inject.class)))
			throw new BeanPostProcessorValidationException("All fields should have proper @Injection", b);
		return supplier;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		if (bean.getClass().getName().startsWith(buildProperties.getGroup()) && !isFramework(bean.getClass())) {
			Annotation[] annotations = bean.getClass().getAnnotations();
			Supplier<Stream<Annotation>> supplier = () -> Arrays.asList(annotations).stream();
			supplier.get().filter(c -> annotationValidationMap.containsKey(c.annotationType()))
					.forEach(c -> annotationValidationMap.get(c.annotationType()).accept(bean));

		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}