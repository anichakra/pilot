package me.anichakra.poc.pilot.framework.web;

/**
 * This exception is thrown if microservice cannot be instantiated.
 * @author anirbanchakraborty
 *
 */
public class MicroserviceInitiationException extends RuntimeException {

	public MicroserviceInitiationException(String className) {
	  super("Cannot start microservice from class:" + className);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
