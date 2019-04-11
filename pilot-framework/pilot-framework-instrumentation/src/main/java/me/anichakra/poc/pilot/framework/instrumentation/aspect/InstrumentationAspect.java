package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.instrumentation.Invocation;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.Layer;

/**
 * It intercepts a method of a class and creates {@link Invocation} instances
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
	
	private boolean enabled;
	private long ignoreDurationInMillis = 10;

	private Map<String, String[]> signatureEventCache = new ConcurrentHashMap<>();

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
		if (!enabled)
			pjp.proceed();
		String signature = pjp.getSignature().toLongString();
		Invocation invocation = new Invocation(signature, layer);
		invocation.setEventBus(eventBus);
		if (signatureEventCache.get(signature) == null) {
			Event e = findEvent(pjp);
			String[] names = new String[] {};
			if (e != null)
				names = e.name();
			signatureEventCache.put(signature, names); // if not extract and add
		}
		
		invocation.setEventNames(signatureEventCache.get(signature)); // set to invocation
		invocation.start(pjp.getArgs());

		Object outcome = null;
		try {
			outcome = pjp.proceed();
		} catch (Throwable t) {
			invocation.failed(t);
			throw t;
		}
		invocation.end(this.ignoreDurationInMillis);
		return outcome;

	}

	private Event findEvent(final ProceedingJoinPoint pjp)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		String signature = pjp.getSignature().toLongString();
		Class<?> type = pjp.getSignature().getDeclaringType();
		String methodName = signature.substring(signature.lastIndexOf(type.getName()) + type.getName().length() + 1,
				signature.indexOf("("));
		String[] parameters = signature.substring(signature.indexOf("(") + 1, signature.lastIndexOf(")")).split(",");

		Class<?>[] parameterTypes = new Class[parameters.length];
		int index = 0;
		for (String parameter : parameters) {
			parameterTypes[index++] = Class.forName(parameter);
		}
		Method method = type.getDeclaredMethod(methodName, parameterTypes);
		return method.getAnnotation(Event.class);
	}

	public void setIgnoreDurationInMillis(int ignoreDurationInMillis) {
		this.ignoreDurationInMillis = ignoreDurationInMillis;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
