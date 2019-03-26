package me.anichakra.poc.pilot.framework.rest.api;

public interface PostConsumer<K,V> extends RestConsumer {
	
	 V consume(K requestBody);

}
