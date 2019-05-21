package me.anichakra.poc.pilot.framework.rest.api;

/**
 * 
 * @author anirbanchakraborty
 *
 * @param <K>
 * @param <V>
 */
public interface PostConsumer<K,V> extends RestConsumer {
	
	/**
	 * 
	 * @param requestBody
	 * @param uriVariables
	 * @return
	 */
	 V consume(K requestBody, Object... uriVariables);
	 
	 /**
	  * 
	  * @param requestBody
	  * @param header
	  * @param uriVariables
	  * @return
	  */
	 V consume(K requestBody, Headers header, Object... uriVariables);

}
