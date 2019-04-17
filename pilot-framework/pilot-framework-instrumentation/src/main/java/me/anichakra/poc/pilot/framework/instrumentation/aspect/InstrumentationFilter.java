package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.Context;
import me.anichakra.poc.pilot.framework.instrumentation.Invocation;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.Layer;

/**
 * The Filter class to intercepts any invocation came into the web application.
 * 
 * @author anichakra
 *
 */
@Component
@WebFilter(displayName = "InstrumentationFilter", urlPatterns = { "/web/*",
		"/pilot-*-service/*" }, description = "Instrumentation Filter")
@ConfigurationProperties(prefix = "instrumentation.filter")
public class InstrumentationFilter implements Filter {

	private final static Logger logger = LogManager.getLogger();
	private boolean enabled;

	@Autowired
	private InvocationEventBus eventBus;

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * Calls the
	 * {{@link #doFilterHttp(HttpServletRequest, HttpServletResponse, FilterChain)}
	 * if request is of type {@linkplain HttpServletRequest}
	 *
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			doFilterHttp((HttpServletRequest) request, (HttpServletResponse) response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * Creates a {@link Invocation} object using request parameters and request
	 * attributes like user, session, ip addresses, headers, etc. by creating the
	 * {@link Context} object and starts the invocation by calling the filter chain.
	 * It ends the invocation after the the chain is completed.
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Invocation invocation = null;
		if (isEnabled()) {
			String user = Optional.ofNullable(request.getUserPrincipal()).map(s -> s.getName())
					.orElse(request.getHeader("X-USER-ID"));

			String sessionId = Optional.ofNullable(request.getSession(false)).map(s -> s.getId()).orElse("");
			Map<String, String[]> parameters = request.getParameterMap();
			invocation = createInvocation(request, user, sessionId, parameters);
            invocation.setEventBus(eventBus);
			try {
				invocation.start(null);
			} catch (Exception e) {
				logger.error("Exception in context.proceed() on start", e);
			}
		}
		try {
			chain.doFilter(request, response);
		} finally {
			if (isEnabled()) {
				try {
					invocation.end(response.getStatus(), 0);
				} catch (Exception e) {
					logger.error("Exception in context.proceed() on completion", e);
				}
			}
		}
	}

	private Invocation createInvocation(HttpServletRequest request, String user, String sessionId,
			Map<String, String[]> parameters) {
		String uri = request.getRequestURI();
		String remoteAddress = getRemoteAddress(request);
		String localAddress = request.getLocalAddr() + ":" + request.getLocalPort();
		Invocation invocation = new Invocation(this.getClass() + ".doFilter()", Layer.FILTER);
		invocation.getContextData().put(Context.CONVERSATION, sessionId);
		invocation.getContextData().put(Context.SOURCE, localAddress);
		invocation.getContextData().put(Context.TARGET, remoteAddress);
		invocation.getContextData().put(Context.PATH, uri);
		invocation.getContextData().put(Context.USER, user);
		invocation.getContextData().put(Context.PARAMETER, getParameters(parameters));
		invocation.getContextData().put(Context.CORRELATION,
				request.getHeader("instrumentation.conversation"));

		return invocation;
	}

	private String getParameters(Map<String, String[]> parameters) {
		if (parameters == null) {
			return "";
		}
		Map<String, String> map1 = new HashMap<>();
		parameters.entrySet().stream().forEach(s -> map1.put(s.getKey(), Arrays.toString(s.getValue())));
		return map1.toString();
	}

	public void destroy() {
	}
	
	private String getRemoteAddress(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("X-Forwarded-For")).orElse(request.getRemoteAddr());
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
