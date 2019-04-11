package me.anichakra.poc.pilot.framework.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("service")
public abstract class ServiceConfiguration {

}
