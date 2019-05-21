package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.annotation.FrameworkService;
import me.anichakra.poc.pilot.framework.rest.api.Headers;
import me.anichakra.poc.pilot.framework.rest.api.PutConsumer;

/**
 * 
 * @author anirbanchakraborty
 *
 * @param <K>
 */

@FrameworkService
public class DefaultPutConsumer<K> extends AbstractRestConsumer implements PutConsumer<K> {

	public DefaultPutConsumer(String name, URI url, Boolean secured) {
		super(name, url, secured);
	}

	@Override
	public void consume(K requestBody, Object... uriVariables) {
		prepareResponseEntity(HttpMethod.PUT, requestBody, Void.class, null, uriVariables);
	}

	@Override
	public void consume(K requestBody, Headers header, Object... uriVariables) {
		prepareResponseEntity(HttpMethod.PUT, requestBody, Void.class, header, uriVariables);

	}

}
