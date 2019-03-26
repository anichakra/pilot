package me.anichakra.poc.pilot.framework.rule.impl;

/**
 * Throw this exception when rule engine cannot be initiated.
 * 
 * @author anirbanchakraborty
 *
 */
public class OpenlTabletsInitiationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new RuleEngineInitiationException
	 * 
	 * @param message
	 * @param e
	 */
	public OpenlTabletsInitiationException(String message, Exception e) {
		super(message, e);
	}

	public OpenlTabletsInitiationException(String message) {
		super(message);
	}

}
