package me.anichakra.poc.pilot.framework.rule.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openl.rules.runtime.RulesEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import me.anichakra.poc.pilot.framework.rule.InvalidRuleTemplatePathException;
import me.anichakra.poc.pilot.framework.rule.RuleEngine;
import me.anichakra.poc.pilot.framework.rule.RuleService;

/**
 * This is the implementation of the abstraction of
 * <a href="http://openl-tablets.org/">openl-tablets</a> rule engine.
 * 
 * @author anirbanchakraborty
 *
 * @param <T>
 */
@Service
public class OpenlTabletsRuleEngine<T> implements RuleEngine<T> {

	@Autowired
	ResourceLoader resourceLoader;

	/**
	 * This returns a RuleService that wraps a {@link RulesEngineFactory}.
	 */
	@Override
	public RuleService<T> configure(String rulePath, Class<T> ruleInterfaceType) throws FileNotFoundException {
		RulesEngineFactory<T> ruleEngineFactory = null;
		synchronized (this) {
			try {
				ruleEngineFactory = new RulesEngineFactory<T>(new URL(rulePath), ruleInterfaceType);
			} catch (MalformedURLException e) {
				System.out.println("ResourceLoader:" + resourceLoader);
				Resource resource = resourceLoader.getResource("classpath:" + rulePath);

				try {
					System.out.println("Resource:" + resource);
					ruleEngineFactory = new RulesEngineFactory<T>(resource.getURL(), ruleInterfaceType);
				} catch (IOException e1) {
					throw new InvalidRuleTemplatePathException("Rule template is invalid or missing: " + rulePath, e);
				}

			}

		}
		return new OpenlTabletsRuleService<T>(ruleEngineFactory);
	}

}
