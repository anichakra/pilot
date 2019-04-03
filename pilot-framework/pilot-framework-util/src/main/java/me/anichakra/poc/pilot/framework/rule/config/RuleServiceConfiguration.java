package me.anichakra.poc.pilot.framework.rule.config;

import java.io.FileNotFoundException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import me.anichakra.poc.pilot.framework.rule.api.RuleEngine;
import me.anichakra.poc.pilot.framework.rule.api.RuleService;
import me.anichakra.poc.pilot.framework.rule.impl.OpenlTabletsRuleEngine;

/**
 * The configuration class for a {@link RuleService}. To configure one has to
 * set in the application.yml the following under prefix "rule":
 * <ul>
 * <li>file-path: It can be an URL (e.g. file://, http://, etc) or a classpath
 * where all the rule files are kept. This can be a directory consists of many
 * rule files or a single rule file. One can specify the directory or file name
 * in the file-path. Above example shows that all rule files are in 'rules'
 * folder that should be in classpath.</li>
 * <li>engine: The rule engine type e.g. OPENL_TABLETS_ORG if its
 * openl-tables.org</li>
 * <li>template-class: The fully qualified class-name of the interface where all
 * the rule methods are kept</li>
 * </ul>
 * <p>
 * To enable this configuration one must set the following configurations in
 * application.yml file:
 * 
 * 
 * @author anirbanchakraborty
 *
 * @param <T>
 */
@Configuration
@ConfigurationProperties(prefix = "rule")
@Validated
@Conditional(RuleServiceConfigurationCondition.class)
public class RuleServiceConfiguration<T> {

	@NotNull
	String filePath;

	@NotNull
	RuleEngine.Type engine;

	@NotNull
	Class<T> templateClass;

	@Inject
	private OpenlTabletsRuleEngine<T> openlTablets;

	/**
	 * 
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 
	 * @param engine
	 */
	public void setEngine(RuleEngine.Type engine) {
		this.engine = engine;
	}

	/**
	 * 
	 * @param templateClass
	 */
	public void setTemplateClass(Class<T> templateClass) {
		this.templateClass = templateClass;
	}

	/**
	 * This is an @Injectable method that returns an instance of the rule template.
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	@Bean
	@Conditional(RuleServiceConfigurationCondition.class)
	public RuleService<T> getRuleService() throws FileNotFoundException {
		switch (engine) {
		case OPENL_TABLETS_ORG:
			return openlTablets.configure(filePath, templateClass);
		default:
			return null;
		}
	}

	@Bean
	public T getRuleTemplate() throws FileNotFoundException {
		switch (engine) {
		case OPENL_TABLETS_ORG:
			return openlTablets.configure(filePath, templateClass).getRuleTemplate();
		default:
			return null;
		}
	}

}
