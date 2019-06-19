package me.anichakra.poc.pilot.framework.instrumentation.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationMetric;

/**
 * The basic configurations for instrumentation. This does not hold the handler
 * specific configurations. Each {@link InvocationEventHandler} has its own
 * configuration.
 * 
 * @author anirbanchakraborty
 *
 */
@Component
@ConfigurationProperties(prefix = "instrumentation")
public class InstrumentationConfig {

	@Autowired(required = false)
	private BuildProperties buildProperties;

	private String name;

	private String version;

	private String startTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

	private boolean enabled;

	private long ignoreDurationInMillis;

	private String environment;

	private String remoteAddressHeader;

	private String userHeader;

	private String instanceId = UUID.randomUUID().toString();

	/**
	 * @return the environment where the container or instance is running.
	 * @see InvocationMetric
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * The duration of an invocation that can be ignored for publishing event and
	 * calling the {@link InvocationEventHandler}s.
	 * 
	 * @return
	 */
	public long getIgnoreDurationInMillis() {
		return ignoreDurationInMillis;
	}

	/**
	 * The container instance id. This is basically the id of the JVM running an
	 * microservice or web application node in a clustered environment.
	 * 
	 * @return the instanceId
	 * @see InvocationMetric
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * The business specific name of the application or microservice. Ideally it
	 * should be the maven or gradle artifact id.
	 * 
	 * @return the name
	 * @see InvocationMetric
	 */
	public String getName() {
		return name;
	}

	/**
	 * The HTTP request header from where the remote address can be fetched. If not
	 * specified then the address will be fetched from request.getRemoteAddr(). This
	 * is required if a proxy is set up between container and user. E.g.
	 * X-HEADER-FOR
	 * 
	 * @return the remoteAddressHeader
	 */
	public String getRemoteAddressHeader() {
		return remoteAddressHeader;
	}

	/**
	 * The start time of the container instance. If not specified from the outside
	 * then the current system time is set.
	 * 
	 * @return the startTime
	 * @see InvocationMetric
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * The HTTP request header from where the user or principle is obtained. If not
	 * specified then request.getPrinciple() is used.
	 * 
	 * @return The user header name
	 * @see InvocationMetric
	 */
	public String getUserHeader() {
		return userHeader;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public void setIgnoreDurationInMillis(long ignoreDurationInMillis) {
		this.ignoreDurationInMillis = ignoreDurationInMillis;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = Optional.ofNullable(instanceId).orElse(UUID.randomUUID().toString());
		;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = Optional.ofNullable(name).orElse(buildProperties.getArtifact());
	}

	/**
	 * @param remoteAddressHeader the remoteAddressHeader to set
	 */
	public void setRemoteAddressHeader(String remoteAddressHeader) {
		this.remoteAddressHeader = remoteAddressHeader;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setUserHeader(String userHeader) {
		this.userHeader = userHeader;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = Optional.ofNullable(version).orElse(buildProperties.getVersion());
		;
	}

}
