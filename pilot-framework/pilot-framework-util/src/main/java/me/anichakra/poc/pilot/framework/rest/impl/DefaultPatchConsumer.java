package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;

import java.util.Optional;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.annotation.FrameworkService;
import me.anichakra.poc.pilot.framework.rest.api.Headers;
import me.anichakra.poc.pilot.framework.rest.api.PatchConsumer;

/**
 * 
 * @author anirbanchakraborty
 *
 * @param <K>
 * @param <V>
 */
@FrameworkService
public class DefaultPatchConsumer<K, V> extends AbstractRestConsumer implements PatchConsumer<K, V> {
	private Class<V> responseType;

	public DefaultPatchConsumer(String name, URI url, Boolean secured, Class<V> responseType) {
		super(name, url, secured);
		this.responseType = Optional.of(responseType).get();
	}

	@Override
	public V consume(K requestBody, Object... uriVariables) {
		return prepareResponseEntity(HttpMethod.PATCH, requestBody, responseType, null, uriVariables).getBody();
	}

	@Override
	public V consume(K requestBody, Headers header, Object... uriVariables) {
		return prepareResponseEntity(HttpMethod.PATCH, requestBody, responseType, header, uriVariables).getBody();

	}
}
