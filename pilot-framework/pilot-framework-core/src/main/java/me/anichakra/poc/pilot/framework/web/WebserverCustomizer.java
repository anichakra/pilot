package me.anichakra.poc.pilot.framework.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebserverCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	@Autowired(required = false)
	private BuildProperties buildProperties;

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		Optional.ofNullable(buildProperties).ifPresent(c -> {
			factory.setContextPath("/" + c.getArtifact() + extractVersion(c.getVersion()));
			System.setProperty("spring.application.name", c.getArtifact());
		});
	}

	private String extractVersion(String c) {
		// major and minor version only
		int firstDot = c.indexOf(".");
		if (firstDot > 0) {
			int secondDot = c.indexOf(".", c.indexOf(".") + 1);
			if (secondDot > 0)
				return "/" + c.substring(0, secondDot);
		}
		return "";
	}

}
