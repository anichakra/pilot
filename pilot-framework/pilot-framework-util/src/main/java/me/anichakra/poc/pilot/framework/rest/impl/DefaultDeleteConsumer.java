package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.rest.api.DeleteConsumer;

public class DefaultDeleteConsumer extends AbstractRestConsumer implements DeleteConsumer {

	public DefaultDeleteConsumer(String name, URI url, Boolean secured) {
		super(name, url, secured);
	}

	@Override
	public void consume() {
		prepareResponseEntity(HttpMethod.DELETE, null, Void.class);
	}

}
