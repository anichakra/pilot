package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.Invocation;
import me.anichakra.poc.pilot.framework.instrumentation.config.InstrumentationConfiguration;

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
public class InstrumentationAspect {

	@Autowired
	private InstrumentationConfiguration config;

	@Around("me.anichakra.poc.pilot.framework.instrumentation.config.InstrumentationConfiguration.executeController()")
	public Object instrumentController(final ProceedingJoinPoint pjp) throws Throwable {
		if (config.isEnabled() && !config.getComponent().isIgnoreController())
			return instrument(pjp);
		else
			return pjp.proceed(pjp.getArgs());
	}

	@Around("me.anichakra.poc.pilot.framework.instrumentation.config.InstrumentationConfiguration.executeService()")
	public Object instrumentService(final ProceedingJoinPoint pjp) throws Throwable {
		if (config.isEnabled() && !config.getComponent().isIgnoreService())
			return instrument(pjp);
		else
			return pjp.proceed(pjp.getArgs());
	}

	@Around("me.anichakra.poc.pilot.framework.instrumentation.config.InstrumentationConfiguration.executeRepository()")
	public Object instrumentRepository(final ProceedingJoinPoint pjp) throws Throwable {
		if (config.isEnabled() && !config.getComponent().isIgnoreRepository())
			return instrument(pjp);
		else
			return pjp.proceed(pjp.getArgs());
	}

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
	 * @return
	 * @throws Throwable
	 */
	public Object instrument(final ProceedingJoinPoint pjp) throws Throwable {
		String signature = pjp.getSignature().toString();
		signature = config.getMethod().isIgnoreSignature() ? signature.substring(signature.indexOf(" "))
				: pjp.getSignature().toLongString();
		Invocation invocation = new Invocation(signature);
		if (config.getMethod().isIgnoreArguments())
			invocation.start(null);
		else
			invocation.start(pjp.getArgs());

		Object outcome = null;
		try {
			outcome = pjp.proceed();
		} catch (Throwable t) {
			invocation.failed(t, config.getMethod().isIgnoreExceptionStack());
			throw t;
		}
		invocation.end(config.getMethod().getIgnoreDurationAbove());
		return outcome;

	}
}
