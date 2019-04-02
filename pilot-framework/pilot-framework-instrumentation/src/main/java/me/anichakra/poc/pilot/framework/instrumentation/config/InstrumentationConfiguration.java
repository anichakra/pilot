package me.anichakra.poc.pilot.framework.instrumentation.config;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The configuration for instrumentation. This holds what methods of which
 * components will be considered for instrumentation.
 * 
 * @author anichakra
 *
 */
@Configuration
@EnableAspectJAutoProxy
@ConfigurationProperties(prefix = "instrumentation")
public class InstrumentationConfiguration {

	/**
	 * Configuring the pointcuts for controller classes using expression.
	 */
	// @Pointcut("execution(* me.anichakra.poc.pilot..controller..*.*(..))")
	// Pointcut("@annotation")
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
	public void executeController() {
	}

	/**
	 * Configuring the pointcuts for service classes using expression.
	 */
	// @Pointcut("execution(* me.anichakra.poc.pilot..service*..*.*(..)) &&
	// !execution(* me.anichakra.poc.pilot.framework..*.*(..))")
	@Pointcut("@annotation(me.anichakra.poc.pilot.framework.annotation.CommandService) "
			+ "|| @annotation(me.anichakra.poc.pilot.framework.annotation.QueryService) "
			+ "|| @annotation(me.anichakra.poc.pilot.framework.annotation.ApplicationService) "
			+ "|| @annotation(me.anichakra.poc.pilot.framework.annotation.FrameworkService)")
	public void executeService() {
	}

	/**
	 * Configuring the pointcuts for repository classes using expression.
	 */
	// @Pointcut("execution(* me.anichakra.poc.pilot..repository*..*.*(..)) &&
	// !execution(* me.anichakra.poc.pilot.framework..*.*(..))")
	@Pointcut("@annotation(org.springframework.stereotype.Repository)")
	public void executeRepository() {
	}

	
	private boolean enabled;
	private Web web = new Web();
	private Component component = new Component();
	private Method method = new Method();
	private Handler handler = new Handler();
	
	public static final class Method {
		private boolean ignoreArguments;
		private boolean ignoreSignature;
		private boolean ignoreExceptionStack;

		private int ignoreDurationAbove = 10;
		
		public boolean isIgnoreArguments() {
			return ignoreArguments;
		}

		public void setIgnoreArguments(boolean ignoreArguments) {
			this.ignoreArguments = ignoreArguments;
		}

		public boolean isIgnoreSignature() {
			return ignoreSignature;
		}

		public void setIgnoreSignature(boolean ignoreSignature) {
			this.ignoreSignature = ignoreSignature;
		}

		public boolean isIgnoreExceptionStack() {
			return ignoreExceptionStack;
		}

		public void setIgnoreExceptionStack(boolean ignoreExceptionStack) {
			this.ignoreExceptionStack = ignoreExceptionStack;
		}

		public int getIgnoreDurationAbove() {
			return ignoreDurationAbove;
		}

		public void setIgnoreDurationAbove(int ignoreDurationAbove) {
			this.ignoreDurationAbove = ignoreDurationAbove;
		}

	}


	public static final class Web {
		private boolean ignoreUriVariables;

		public boolean isIgnoreUriVariables() {
			return ignoreUriVariables;
		}

		public void setIgnoreUriVariables(boolean ignoreUriVariables) {
			this.ignoreUriVariables = ignoreUriVariables;
		}
	}


	public static final class Handler {
		private Log log = new Log();
		private NewRelic newRelic = new NewRelic();

		public Log getLog() {
			return log;
		}

		public void setLog(Log log) {
			this.log = log;
		}

		public NewRelic getNewRelic() {
			return newRelic;
		}

		public void setNewRelic(NewRelic newRelic) {
			this.newRelic = newRelic;
		}
	}

	public static final class Log {
		private boolean enabled;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}

	public static final class NewRelic {
		private boolean enabled;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}

	public static final class Component {
		private boolean ignoreController = false;
		private boolean ignoreService = false;
		private boolean ignoreRepository = false;

		public boolean isIgnoreController() {
			return ignoreController;
		}

		public void setIgnoreController(boolean ignoreController) {
			this.ignoreController = ignoreController;
		}

		public boolean isIgnoreService() {
			return ignoreService;
		}

		public void setIgnoreService(boolean ignoreService) {
			this.ignoreService = ignoreService;
		}

		public boolean isIgnoreRepository() {
			return ignoreRepository;
		}

		public void setIgnoreRepository(boolean ignoreRepository) {
			this.ignoreRepository = ignoreRepository;
		}
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Web getWeb() {
		return web;
	}

	public void setWeb(Web web) {
		this.web = web;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
