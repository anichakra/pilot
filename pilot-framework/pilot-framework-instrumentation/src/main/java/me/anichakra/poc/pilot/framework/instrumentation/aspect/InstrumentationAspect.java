package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.Invocation;

/**
 * It intercepts a method of a class and creates {@link Invocation} instances during start, completion and failure of a
 * method call.
 * 
 * @see InstrumentationConfig
 * @author anichakra
 *
 */
@Aspect
@Component
@ConfigurationProperties(prefix = "framework.instrumentation")
public class InstrumentationAspect {

    private boolean enabled;
    private boolean rootCauseOnly;
    private int durationToIgnore = 10;
    private boolean includeMethodParams;
    private boolean includeMethodFullSignature;
    
	private boolean controllerExecution = false;
	private boolean serviceExecution = false;
	private boolean repositoryExecution = false;


    /**
     * If only the root cause of a failed conversation need to captured
     * 
     * @param rootCauseOnly
     */
    public void setRootCauseOnly(boolean rootCauseOnly) {
        this.rootCauseOnly = rootCauseOnly;
    }

    /**
     * If the duration of an invocation takes less than durationToIgnore then the invocation will be
     * ignored and context will not be set.
     * 
     * @param durationToIgnore
     */
    public void setDurationToIgnore(int durationToIgnore) {
        this.durationToIgnore = durationToIgnore;
    }

    /**
     * If the method parameters also need to be set to the {@link InvocationContext}.
     * 
     * @param includeMethodParams
     */
    public void setIncludeMethodParams(boolean includeMethodParams) {
        this.includeMethodParams = includeMethodParams;
    }

    /**
     * If the full method signature need to be send as part of the {@link InvocationContext}.
     * 
     * @param includeMethodFullSignature
     */
    public void setIncludeMethodFullSignature(boolean includeMethodFullSignature) {
        this.includeMethodFullSignature = includeMethodFullSignature;
    }
    
    
    @Around("InstrumentationConfig.executeController()")
    public Object instrumentController(final ProceedingJoinPoint pjp) throws Throwable {
        return instrument(isControllerExecution(), pjp);
    }
    
    @Around("InstrumentationConfig.executeService()")
    public Object instrumentService(final ProceedingJoinPoint pjp) throws Throwable {
    	return instrument(isServiceExecution(), pjp);
    }
    
    @Around("InstrumentationConfig.executeRepository()")
    public Object instrumentRepository(final ProceedingJoinPoint pjp) throws Throwable {
    	return instrument(isRepositoryExecution(), pjp);
    }
    

    /**
     * All pointcuts defined in {@link InstrumentationConfig} will be made as target to profile.
     * {@link InvocationContext} instances will be created before and after calling the joint-point
     * method. If the invocation target method fails with an exception, a failed
     * {@link InvocationContext} instance will be created. The proceed() method of the
     * InvocationContext will be called for all the three cases so that necessary steps can be taken
     * by the implementing components of the InvocationContext interface.
     * 
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object instrument(final boolean instrument, final ProceedingJoinPoint pjp) throws Throwable {
		String signature = pjp.getSignature().toString();
		signature = includeMethodFullSignature ? pjp.getSignature().toLongString()
				: signature.substring(signature.indexOf(" "));
		Invocation invocation = new Invocation(signature);
		invocation.setFireInvocationEvent(enabled);
		if (includeMethodParams)
		invocation.start(pjp.getArgs());
		Object outcome = null;
		try {
			outcome = pjp.proceed();
		} catch (Throwable t) {
			invocation.failed(t, rootCauseOnly);
			throw t;
		}
		invocation.end(durationToIgnore);
		return outcome;
		
    }

    /**
     * 
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isControllerExecution() {
		return controllerExecution;
	}

	public void setControllerExecution(boolean controllerExecution) {
		this.controllerExecution = controllerExecution;
	}

	public boolean isServiceExecution() {
		return serviceExecution;
	}

	public void setServiceExecution(boolean serviceExecution) {
		this.serviceExecution = serviceExecution;
	}

	public boolean isRepositoryExecution() {
		return repositoryExecution;
	}

	public void setRepositoryExecution(boolean repositoryExecution) {
		this.repositoryExecution = repositoryExecution;
	}

}
