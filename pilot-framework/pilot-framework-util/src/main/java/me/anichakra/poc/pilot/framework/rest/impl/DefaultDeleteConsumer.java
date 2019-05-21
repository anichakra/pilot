package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;

import me.anichakra.poc.pilot.framework.annotation.FrameworkService;
import me.anichakra.poc.pilot.framework.rest.api.DeleteConsumer;
import me.anichakra.poc.pilot.framework.rest.api.Headers;

/**
 * 
 * @author anirbanchakraborty
 *
 */
@FrameworkService
public class DefaultDeleteConsumer extends AbstractRestConsumer implements DeleteConsumer {

	public DefaultDeleteConsumer(String name, URI url, Boolean secured) {
		super(name, url, secured);
	}

	@Override
	public void consume(Object... uriVariables) {
		prepareResponseEntity(HttpMethod.DELETE, null, Void.class, null, uriVariables);
	}

	@Override
	public void consume(Headers header, Object... uriVariables) {
		prepareResponseEntity(HttpMethod.DELETE, null, Void.class, header);
		
	}

}
