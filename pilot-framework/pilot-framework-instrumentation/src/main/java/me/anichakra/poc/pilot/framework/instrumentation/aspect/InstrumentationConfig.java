package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The configuration for instrumentation. This holds what methods of which components will be considered
 * for instrumentation.
 * 
 * @author anichakra
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class InstrumentationConfig {

    /**
     * Configuring the pointcuts for controller classes using expression.
     */
    @Pointcut("execution(* me.anichakra.poc.pilot..controller..*.*(..)) && !@annotation(me.anichakra.poc.pilot.framework.instrumentation.aspect.IgnoreInstrumentation)")
    public void executeController() {
    }

    /**
     * Configuring the pointcuts for service classes using expression.
     */
    @Pointcut("execution(* me.anichakra.poc.pilot..service*..*.*(..)) && !execution(* me.anichakra.poc.pilot.framework..*.*(..)) && !@annotation(me.anichakra.poc.pilot.framework.instrumentation.aspect.IgnoreInstrumentation)")
    public void executeService() {
    }

    /**
     * Configuring the pointcuts for repository classes using expression.
     */
    @Pointcut("execution(* me.anichakra.poc.pilot..repository*..*.*(..)) && !execution(* me.anichakra.poc.pilot.framework..*.*(..)) && !@annotation(me.anichakra.poc.pilot.framework.instrumentation.aspect.IgnoreInstrumentation)")
    public void executeRepository() {
    }
}
