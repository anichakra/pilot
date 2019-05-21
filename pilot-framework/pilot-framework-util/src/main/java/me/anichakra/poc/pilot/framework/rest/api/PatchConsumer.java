package me.anichakra.poc.pilot.framework.rest.api;

public interface PatchConsumer<K, V> extends RestConsumer {
	V consume(K requestBody, Object... uriVariables);

	V consume(K requestBody, Headers header, Object... uriVariables);

}
