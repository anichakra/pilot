package me.anichakra.poc.pilot.framework.rule;

import java.util.Map;

public interface RuleService<T> {
	
	public T getRuleTemplate();

	public T getRuleTemplate(String contextName);

	public T getRuleTemplate(String contextName, Map<String, Object> params);
}
