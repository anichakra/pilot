package me.anichakra.poc.pilot.framework.rule.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openl.rules.runtime.RulesEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import me.anichakra.poc.pilot.framework.rule.api.RuleEngine;
import me.anichakra.poc.pilot.framework.rule.api.RuleService;

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
	public RuleService<T> configure(String rulePath, Class<T> ruleInterfaceType) {
		RulesEngineFactory<T> ruleEngineFactory = null;
		URL url = null;
		synchronized (this) {
			try {
				url = new URL(rulePath); // first check if its an URL
			} catch (MalformedURLException e) {
				Resource resource = resourceLoader.getResource("classpath:" + rulePath);
				try {
					url = resource.getURL(); // load from classpath
					String file = url.getFile();
					File path = new File(file);
					if (path.isDirectory()) {
						String[] files = path.list();
						if (files.length == 0) {
							throw new OpenlTabletsInitiationException(rulePath + "does not contain any rule file!");
						}
						for (String f : files) {
							if (f.endsWith(".xls") || f.endsWith(".xlsx")) {
								String name = new File(f).getName();
								url = new URL(url.toString() + "/" + name);
							} else {
								throw new OpenlTabletsInitiationException(rulePath + "does not contain any rule file!");
							}
						}
					}

				} catch (IOException ie) {
					throw new OpenlTabletsInitiationException("Resource: " + rulePath + "cannot be loaded!", ie);
				}
			}
			ruleEngineFactory = new RulesEngineFactory<T>(url, ruleInterfaceType);
		}
		return new OpenlTabletsRuleService<T>(ruleEngineFactory);
	}

}
