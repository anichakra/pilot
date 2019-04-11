package me.anichakra.poc.pilot.framework.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import me.anichakra.poc.pilot.framework.annotation.FrameworkService;

@Configuration
@ConfigurationProperties("service")
@FrameworkService
public abstract class ServiceConfiguration {
   
}
