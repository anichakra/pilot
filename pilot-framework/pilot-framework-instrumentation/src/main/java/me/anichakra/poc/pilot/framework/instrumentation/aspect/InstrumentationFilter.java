package me.anichakra.poc.pilot.framework.instrumentation.aspect;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import javax.naming.Context;
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
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.Invocation;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBus;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric;
import me.anichakra.poc.pilot.framework.instrumentation.Layer;
import me.anichakra.poc.pilot.framework.instrumentation.config.InstrumentationConfig;

/**
 * The Filter class to intercepts any invocation made to the web application.
 * 
 * @author anichakra
 *
 */
@Component
@WebFilter(displayName = "InstrumentationFilter", urlPatterns = { "/web/*",
		"/pilot-*-service/*" }, description = "Instrumentation Filter")
public class InstrumentationFilter implements Filter {

	private static final String CORRELATION = "INSTRUMENTATION_CORRELATION";
	private final static Logger logger = LogManager.getLogger();
	private final String SIGNATURE = this.getClass().getName() + ".doFilter()";

	@Autowired
	private InvocationEventBus eventBus;

	@Autowired
	private InstrumentationConfig config;

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
		if (config.isEnabled()) {
			Map<String, String[]> parameters = request.getParameterMap();
			invocation = new Invocation(Layer.FILTER, eventBus);
			invocation.setEventBus(eventBus);
			populateInvocationMetric(request, invocation);
			try {
				invocation.start(SIGNATURE, getParameters(parameters));

			} catch (Exception e) {
				logger.error("Exception in context.proceed() on start", e);
			}
		}
		try {
			chain.doFilter(request, response);
		} finally {
			if (config.isEnabled()) {
				try {
					invocation.end(response.getStatus());
				} catch (Exception e) {
					logger.error("Exception in context.proceed() on completion", e);
				}
			}
		}
	}

	private void populateInvocationMetric(HttpServletRequest request, Invocation invocation) {
		String user = getUser(request);
		String traceId = getTraceId(request);
		String uri = "[" + request.getMethod() + "]" + request.getRequestURI();
		String remoteAddress = getRemoteAddress(request);
		String localAddress = request.getLocalAddr() + ":" + request.getLocalPort() + " "
				+ Optional.ofNullable(System.getenv("HOSTNAME")).orElse("");
		invocation.addMetric(InvocationMetric.NAME, config.getName())
				.addMetric(InvocationMetric.VERSION, config.getVersion())
				.addMetric(InvocationMetric.START_TIME, config.getStartTime())
				.addMetric(InvocationMetric.INSTANCE_ID, config.getInstanceId())
				.addMetric(InvocationMetric.TRACE_ID, traceId)
				.addMetric(InvocationMetric.ENVIRONMENT, config.getEnvironment())
				.addMetric(InvocationMetric.CORRELATION_ID, request.getHeader(CORRELATION))
				.addMetric(InvocationMetric.REMOTE_ADDRESS, remoteAddress)
				.addMetric(InvocationMetric.LOCAL_ADDRESS, localAddress).addMetric(InvocationMetric.USER_ID, user)
				.addMetric(InvocationMetric.URI, uri);
	}

	private Object[] getParameters(Map<String, String[]> parameters) {
		StringBuilder sb = new StringBuilder();
		Optional.ofNullable(parameters).ifPresent(p -> {
			p.entrySet().stream().forEach(s -> sb.append(s.getKey()).append("=").append(Arrays.toString(s.getValue())));
			sb.append(";");
		});
		String returnVal = sb.toString();
		if (returnVal.endsWith(";")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString().split(";");
	}

	private String getTraceId(HttpServletRequest request) {
		return Optional.ofNullable(request.getSession(false)).map(s -> s.getId())
				.orElse(request.getRequestedSessionId());
	}

	private String getRemoteAddress(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(config.getRemoteAddressHeader())).orElse(request.getRemoteAddr());
	}

	private String getUser(HttpServletRequest request) {
		if (config.getUserHeader() == null)
			return request.getUserPrincipal().getName();
		else
			return request.getHeader(config.getUserHeader());
	}

	public void destroy() {
	}
}
