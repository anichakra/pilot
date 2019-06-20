package me.anichakra.poc.pilot.framework.instrumentation.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventBuilder;
import me.anichakra.poc.pilot.framework.instrumentation.aspect.InstrumentationFilter;

public class RestTemplateImpl extends RestTemplate {

	public RestTemplateImpl(ClientHttpRequestFactory requestFactory) {
		super(requestFactory);
	}

	@Override
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType, Object... uriVariables) {
		HttpHeaders newHeaders = new HttpHeaders();
		newHeaders.addAll(requestEntity.getHeaders());
		newHeaders.add(InstrumentationFilter.CORRELATION, InvocationEventBuilder.getCurrent().getId());
		return super.exchange(url, method, new HttpEntity<>(requestEntity.getBody(), newHeaders), responseType,
				uriVariables);
	}

	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType) {
		HttpHeaders newHeaders = new HttpHeaders();
		newHeaders.addAll(requestEntity.getHeaders());
		newHeaders.add(InstrumentationFilter.CORRELATION, InvocationEventBuilder.getCurrent().getId());
		return super.exchange(url, method, new HttpEntity<>(requestEntity.getBody(), newHeaders), responseType);
	}

}
