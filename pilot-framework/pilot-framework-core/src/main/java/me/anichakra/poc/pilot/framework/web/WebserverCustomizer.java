package me.anichakra.poc.pilot.framework.web;

import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
public class WebserverCustomizer implements  WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
  
	
	private String contextPath;

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public void customize (ConfigurableServletWebServerFactory factory) {
		Optional.ofNullable(contextPath).ifPresent(c -> {
			factory.setContextPath(extractVersion(c));
			System.setProperty("spring.application.name", extractVersion(c).replaceFirst("/", "").replaceAll("/", "-"));
		});
	}

	private String extractVersion(String c) {
		// major and minor version only
		return c.substring(0, c.indexOf(".", c.indexOf(".") + 1));
	}

}
