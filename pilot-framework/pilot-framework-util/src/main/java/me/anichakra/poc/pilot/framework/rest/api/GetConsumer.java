package me.anichakra.poc.pilot.framework.rest.api;

public interface GetConsumer<V> extends RestConsumer{
	/**
	 *  This method invoke Rest call and return the the object of Type T where type T is declared in yml file
	 * @return 
	 */
	 V consume(Object... uriVariables);
	 
	 V consume(Headers header, Object... uriVariables);



}
