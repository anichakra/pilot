package me.anichakra.poc.pilot.framework.rule.api;

import java.util.Map;

/**
 * The rule service wraps the rule template and returns the same. This is
 * created by {@link RuleEngine} and each rule engine that is abstracted by this
 * framework should have an iimplementation of rule service.
 * 
 * @author anirbanchakraborty
 *
 * @param <T> The rule template class type.
 */
public interface RuleService<T> {

	/**
	 * 
	 * @return The rule template instance
	 */
	public T getRuleTemplate();

	/**
	 * 
	 * @param contextName The name of the context if there a more than one contexts
	 *                    for this rule template.
	 * @return The rule template instance
	 */
	public T getRuleTemplate(String contextName);

	/**
	 * 
	 * @param contextName The name of the context if there a more than one contexts
	 *                    for this rule template.
	 * @param params A map of parameters require to configure the rule template.
	 * @return The rule template instance
	 */
	public T getRuleTemplate(String contextName, Map<String, Object> params);
}
