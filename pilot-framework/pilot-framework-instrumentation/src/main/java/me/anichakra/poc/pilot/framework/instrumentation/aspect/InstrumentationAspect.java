package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import me.anichakra.poc.pilot.framework.annotation.ApplicationService;
import me.anichakra.poc.pilot.framework.annotation.CommandService;
import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.annotation.FrameworkService;
import me.anichakra.poc.pilot.framework.annotation.QueryService;
import me.anichakra.poc.pilot.framework.annotation.processor.EventAnnotationDetectionProcessor;
import me.anichakra.poc.pilot.framework.instrumentation.Invocation;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.Layer;
import me.anichakra.poc.pilot.framework.instrumentation.config.InstrumentationConfig;

/**
 * It intercepts a method of a {@link RestController}, {@link CommandService},
 * {@link QueryService}, {@link ApplicationService}, {@link FrameworkService} or
 * {@link Repository} annotated class and creates {@link Invocation} instances
 * during start, completion and failure of a method call.
 * 
 * @see InstrumentationConfiguration
 * @author anichakra
 *
 */
@Aspect
@Component
@ConfigurationProperties(prefix = "instrumentation.aspect")
public class InstrumentationAspect {
	@Autowired
	private InvocationEventBus eventBus;

	@Autowired
	private EventAnnotationDetectionProcessor eventAnnotationDetectionProcessor;

	@Autowired
	private InstrumentationConfig config;

	@Around("controllerClassMethods()")
	public Object instrumentController(final ProceedingJoinPoint pjp) throws Throwable {
		return instrument(pjp, Layer.CONTROLLER);
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controllerClassMethods() {
	};

	@Around("serviceClassMethods()")
	public Object instrumentService(final ProceedingJoinPoint pjp) throws Throwable {
		return instrument(pjp, Layer.SERVICE);
	}

	@Pointcut("within(@me.anichakra.poc.pilot.framework.annotation.CommandService *) "
			+ "|| within(@me.anichakra.poc.pilot.framework.annotation.QueryService *) "
			+ "|| within(@me.anichakra.poc.pilot.framework.annotation.ApplicationService *) "
			+ "|| within(@me.anichakra.poc.pilot.framework.annotation.FrameworkService *)")
	public void serviceClassMethods() {
	};

	@Around("repositoryClassMethods()")
	public Object instrumentRepository(final ProceedingJoinPoint pjp) throws Throwable {
		return instrument(pjp, Layer.REPOSITORY);

	}

	@Pointcut("within(@org.springframework.stereotype.Repository *)")
	public void repositoryClassMethods() {
	};

	/**
	 * All pointcuts defined in {@link InstrumentationConfig} will be made as target
	 * to profile. {@link InvocationContext} instances will be created before and
	 * after calling the joint-point method. If the invocation target method fails
	 * with an exception, a failed {@link InvocationContext} instance will be
	 * created. The proceed() method of the InvocationContext will be called for all
	 * the three cases so that necessary steps can be taken by the implementing
	 * components of the InvocationContext interface.
	 * 
	 * @param pjp
	 * @param layer
	 * @return
	 * @throws Throwable
	 */
	public Object instrument(final ProceedingJoinPoint pjp, Layer layer) throws Throwable {
		if (!config.isEnabled())
			return pjp.proceed();
		String signature = pjp.getSignature().toLongString();
		Invocation invocation = new Invocation(layer, eventBus);
		invocation.setEventBus(eventBus);
		Event event = eventAnnotationDetectionProcessor.getEventAnnotation(signature);
		invocation.setEvent(event); // set to invocation
		invocation.start(signature, pjp.getArgs());

		Object outcome = null;
		try {
			outcome = pjp.proceed();
		} catch (Throwable t) {
			invocation.failed(t);
			throw t;
		}
		invocation.setDurationToIgnore(config.getIgnoreDurationInMillis());
		invocation.end(outcome);

		return outcome;
	}
}
