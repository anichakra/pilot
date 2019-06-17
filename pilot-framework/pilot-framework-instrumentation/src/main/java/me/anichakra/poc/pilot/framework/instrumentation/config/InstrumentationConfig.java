package me.anichakra.poc.pilot.framework.instrumentation.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "instrumentation")
public class InstrumentationConfig {

	@Autowired(required = false)
	private BuildProperties buildProperties;

	private String name;

	private String version;

	private String startTime=LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

	private boolean enabled;

	private long ignoreDurationInMillis;

	private String instanceId = UUID.randomUUID().toString();

	public long getIgnoreDurationInMillis() {
		return ignoreDurationInMillis;
	}

	public void setIgnoreDurationInMillis(long ignoreDurationInMillis) {
		this.ignoreDurationInMillis = ignoreDurationInMillis;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId =  Optional.ofNullable(instanceId).orElse(UUID.randomUUID().toString());;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = Optional.ofNullable(name).orElse(buildProperties.getArtifact());
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = Optional.ofNullable(version).orElse(buildProperties.getVersion());
		;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
