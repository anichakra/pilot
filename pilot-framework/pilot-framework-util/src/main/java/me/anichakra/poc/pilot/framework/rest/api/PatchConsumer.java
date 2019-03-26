package me.anichakra.poc.pilot.framework.rest.api;

public interface PatchConsumer<K, V> extends RestConsumer {
	V consume(K requestBody);

}
