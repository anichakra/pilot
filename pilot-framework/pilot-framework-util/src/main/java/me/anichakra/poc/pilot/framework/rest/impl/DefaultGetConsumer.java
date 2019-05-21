package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.annotation.FrameworkService;
import me.anichakra.poc.pilot.framework.rest.api.GetConsumer;
import me.anichakra.poc.pilot.framework.rest.api.Headers;
/**
 * 
 * @author anirbanchakraborty
 *
 * @param <V>
 */
@FrameworkService
public class DefaultGetConsumer<V> extends AbstractRestConsumer implements GetConsumer<V> {

	private Class<V> responseType;

	public DefaultGetConsumer(String name, URI url, Boolean secured, Class<V> responseType) {
		super(name, url, secured);
		this.responseType = Optional.of(responseType).get();
	}

	@Override
	public V consume(Object... uriVariables) {
		return prepareResponseEntity(HttpMethod.GET, null, responseType, null, uriVariables).getBody();
	}

	@Override
	public V consume(Headers header, Object... uriVariables) {
		return prepareResponseEntity(HttpMethod.GET, null, responseType, header, uriVariables).getBody();

	}

}
