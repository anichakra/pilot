package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.rest.api.PatchConsumer;

public class DefaultPatchConsumer<K, V> extends AbstractRestConsumer implements PatchConsumer<K, V> {
	private Class<V> responseType;

	public DefaultPatchConsumer(String name, URI url, Boolean secured, Class<V> responseType) {
		super(name, url, secured);
		this.responseType = Optional.of(responseType).get();
	}

	@Override
	public V consume(K requestBody) {
		return prepareResponseEntity(HttpMethod.PATCH, requestBody, responseType).getBody();
	}
}
