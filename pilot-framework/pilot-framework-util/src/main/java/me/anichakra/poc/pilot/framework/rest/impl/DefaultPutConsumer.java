package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.rest.api.PutConsumer;

public class DefaultPutConsumer<K> extends AbstractRestConsumer implements PutConsumer<K> {

	public DefaultPutConsumer(String name, URI url, Boolean secured) {
		super(name, url, secured);
	}

	@Override
	public void consume(K requestBody) {
		prepareResponseEntity(HttpMethod.PUT, requestBody, Void.class);
}

}
