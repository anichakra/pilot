package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.rest.api.GetConsumer;

public class DefaultGetConsumer<V> extends AbstractRestConsumer implements GetConsumer<V> {

	private Class<V> responseType;

	public DefaultGetConsumer(String name, URI url, Boolean secured, Class<V> responseType) {
		super(name, url, secured);
		this.responseType = Optional.of(responseType).get();
	}

	@Override
	public V consume() {
		return prepareResponseEntity(HttpMethod.GET, null, responseType).getBody();
	}

}
