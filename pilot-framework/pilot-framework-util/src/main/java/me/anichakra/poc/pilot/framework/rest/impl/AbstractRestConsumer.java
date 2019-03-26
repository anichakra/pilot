package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import me.anichakra.poc.pilot.framework.rest.api.RestConsumer;

public class AbstractRestConsumer implements RestConsumer {
	private String name;
	private Boolean secured = false;
	protected URI url;
	protected String contentType;
	protected String accept;
	protected HttpStatus httpStatusCode;
	protected HttpHeaders headers = new HttpHeaders();
	protected Object[] uriVariables = null;
	protected RestTemplate restTemplate;

	protected AbstractRestConsumer(String name, URI url, Boolean secured) {
		this.name = name;
		this.url = url;
		this.secured = secured;
		initialize();
	}

	protected void initialize() {
		initializeRestTemplate();
	}

	private void initializeRestTemplate() {
		if (secured) {
			try {
				SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy())
						.build();
				SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
				HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setHttpClient(httpClient);
				this.restTemplate = new RestTemplate(requestFactory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.restTemplate = new RestTemplate();
		}
	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public HttpStatus getStatusCode() {
		return httpStatusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.httpStatusCode = statusCode;
	}

	public URI getUrl() {
		return url;
	}

	@Override
	public RestConsumer addHeader(String name, String value) {
		headers.add(name, value);
		return this;
	}

	@Override
	public RestConsumer setUriVariables(Object... uriVariables) {
		this.uriVariables = uriVariables;
		return this;
	}

	protected <K, V> ResponseEntity<V> prepareResponseEntity(HttpMethod method, K requestBody, Class<V> responseType) {
		Optional.ofNullable(contentType).ifPresent(c -> headers.add(HttpHeaders.CONTENT_TYPE, c));
		Optional.ofNullable(accept).ifPresent(c -> headers.add("Accept", c));

		ResponseEntity<V> responseEntity = uriVariables != null
				? restTemplate.exchange(url.toString(), method, new HttpEntity<K>(requestBody), responseType,
						uriVariables)
				: restTemplate.exchange(url.toString(), method, new HttpEntity<K>(requestBody), responseType);
		if (httpStatusCode != null && responseEntity.getStatusCodeValue() != httpStatusCode.value())
			throw new InvalidReturnedStatusCodeException(url.toString(), httpStatusCode.value());
		return responseEntity;
	}

}