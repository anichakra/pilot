package me.anichakra.poc.pilot.framework.rest.api;

/**
 * 
 * @author anirbanchakraborty
 *
 * @param <K>
 */
public interface PutConsumer<K> extends RestConsumer{
	
	/**
	 *  This method invoke takes input object V as Entity and Rest call and return the the object of Type T where type T is declared in yml file
	 * @return 
	 */
	 void consume(K requestBody, Object... uriVariables);
	 
	 /**
	  * 
	  * @param requestBody
	  * @param header
	  * @param uriVariables
	  */
	 void consume(K requestBody, Headers header, Object... uriVariables);


}
