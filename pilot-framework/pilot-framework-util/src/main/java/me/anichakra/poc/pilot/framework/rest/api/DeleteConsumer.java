package me.anichakra.poc.pilot.framework.rest.api;

/**
 * The interface for http delete method
 * @author anirbanchakraborty
 *
 */
public interface DeleteConsumer extends RestConsumer{
	
	/**
	 * Consume a delete method
	 */
	 void consume(Object... uriVariables);

	 void consume(Headers header, Object... uriVariables);

}
