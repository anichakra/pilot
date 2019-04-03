package me.anichakra.poc.pilot.framework.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * This component customizes the context-path of the web application. The
 * context-path is taken from the maven artifactId and version (only the major
 * and minor, from the bug-fix part it is ignored). It also sets the
 * "spring.application.name" to the artifactId. So if the artifactId is
 * my-micro-service and version is 1.2.3.BUILD-SNAPSHOT, then the context-path
 * will be: /my-micro-service/1.2. For e.g. for a API called vehicle/preference
 * the entire POST URL will be
 * http://mysite.com/my-micro-service/1.2/vehicle/preference.
 * <p>
 * To achieve this the following plugin goal configuration need to be done in
 * pom.xml:
 * <p>
 * <pre>{@code
 * <plugin>
 *   <groupId>org.springframework.boot</groupId>
 *   <artifactId>spring-boot-maven-plugin</artifactId>
 *   <version>...</version> 
 *   <executions> 
 *     <execution>
 *       <id>build-info</id>
 *       <goals> 
 *         <goal>build-info</goal> 
 *         <goal>repackage</goal>
 *       </goals> 
 *     </execution> 
 *   </executions> 
 * </plugin> 
 * }</pre>
 * 
 * @author anirbanchakraborty
 *
 */
@Component
public class WebserverCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	private static final String SEPARATOR = "/";
	@Autowired(required = false)
	private BuildProperties buildProperties;

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		Optional.ofNullable(buildProperties).ifPresent(c -> {
			factory.setContextPath(SEPARATOR + c.getArtifact() + extractVersion(c.getVersion()));
			System.setProperty("spring.application.name", c.getArtifact());
		});
	}

	private String extractVersion(String c) {
		// major and minor version only
		int firstDot = c.indexOf(".");
		if (firstDot > 0) {
			int secondDot = c.indexOf(".", c.indexOf(".") + 1);
			if (secondDot > 0)
				return SEPARATOR + c.substring(0, secondDot);
		}
		return "";
	}

}
