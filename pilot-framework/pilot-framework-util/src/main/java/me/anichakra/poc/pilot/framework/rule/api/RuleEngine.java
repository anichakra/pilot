package me.anichakra.poc.pilot.framework.rule.api;

import java.io.FileNotFoundException;

/**
 * This abstract any open source or commercial java rule engines, initiate the
 * same and returns a {@link RuleService}.
 * 
 * @author anirbanchakraborty
 *
 * @param <T>
 */
public interface RuleEngine<T> {
	/**
	 * This enum contains all the different type of rule engines that this framework
	 * is going to abstract.
	 * 
	 * @author anirbanchakraborty
	 *
	 */
	public static enum Type {
		OPENL_TABLETS_ORG; // TODO: more may come
	}

	/**
	 * Configure the rule engine and returs a rule service. All implementation class
	 * should abstract the technical details of configuring the rule engine in this
	 * method.
	 * 
	 * @param rulePath     The path where the rule file(s) are kept. It can be a
	 *                     string representation of URL or classpath.
	 * @param templateType The class type of the rule template. The rule template is
	 *                     a standard interface containing the methods of the rule.
	 * @return The RuleService instance that will wrap the rule template.
	 * @throws FileNotFoundException
	 */
	RuleService<T> configure(String rulePath, Class<T> templateType) throws FileNotFoundException;

}
