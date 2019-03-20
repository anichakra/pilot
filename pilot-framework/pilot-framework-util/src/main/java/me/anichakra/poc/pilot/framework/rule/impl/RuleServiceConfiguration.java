package me.anichakra.poc.pilot.framework.rule.impl;

import java.io.FileNotFoundException;

import javax.validation.constraints.NotNull;

import me.anichakra.poc.pilot.framework.annotation.Inject;
import me.anichakra.poc.pilot.framework.annotation.Injectable;
import me.anichakra.poc.pilot.framework.rule.RuleEngine;
import me.anichakra.poc.pilot.framework.rule.RuleService;

public abstract class RuleConfigService<T> {

	@NotNull
	String templatePath;

	@NotNull
	RuleEngine.Type engine;

	@Inject
	private OpenlTabletsRuleEngine<T> openlTablets;

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public void setEngine(RuleEngine.Type engine) {
		this.engine = engine;
	}

	@Injectable
	public RuleService<T> getRuleService() throws FileNotFoundException {
		switch (engine) {
		case OPENL_TABLETS_ORG:
			return openlTablets.configure(templatePath, getRuleTemplateClass());
		default:
			return null;
		}
	}
	
	@Injectable
	public T getRuleTemplate() throws FileNotFoundException {
		switch (engine) {
		case OPENL_TABLETS_ORG:
			return openlTablets.configure(templatePath, getRuleTemplateClass()).getRuleTemplate();
		default:
			return null;
		}
	}


	protected abstract Class<T> getRuleTemplateClass();
}
