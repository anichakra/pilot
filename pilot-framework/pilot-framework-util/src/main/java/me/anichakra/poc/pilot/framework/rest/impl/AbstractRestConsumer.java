package me.anichakra.poc.pilot.framework.rest.impl;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import me.anichakra.poc.pilot.framework.rest.api.Headers;
import me.anichakra.poc.pilot.framework.rest.api.RestConsumer;

/**
 * 
 * @author anirbanchakraborty
 *
 */
public class AbstractRestConsumer implements RestConsumer {
	private String name;
	private Boolean secured = false;
	protected URI url;
	protected String contentType;
	protected String accept;
	protected HttpStatus httpStatusCode;
	protected HttpHeaders headers = new HttpHeaders();
	Map<String, String> parameters = new HashMap<>();
	protected RestTemplate restTemplate;
	private boolean prepared;

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
			SSLContext sslContext;
			try {
				sslContext = SSLContexts.custom().loadTrustMaterial(getKeyStore(), getTrustStrategy()).build();
				SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
				HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setHttpClient(httpClient);
				this.restTemplate = new RestTemplate(requestFactory);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
				throw new RestConsumerInitializationException("Cannot create secured rest consumer", e);
			}
		} else {
			this.restTemplate = new RestTemplate();
		}
	}

	protected KeyStore getKeyStore() {
		return null;
	}

	protected TrustStrategy getTrustStrategy() {
		return new TrustAllStrategy();
	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
		Optional.ofNullable(contentType).ifPresent(c -> headers.add(HttpHeaders.CONTENT_TYPE, c));
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
		Optional.ofNullable(accept).ifPresent(c -> headers.add(HttpHeaders.ACCEPT, c));
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

	public RestConsumer addHeader(String name, String value) {
		headers.add(name, value);
		return this;
	}

	public RestConsumer addProperty(String name, String value) {
		parameters.put(name, value);
		return this;
	}

	public String getProperty(String name) {
		return parameters.get(name);
	}


	protected <K, V> ResponseEntity<V> prepareResponseEntity(HttpMethod method, K requestBody, Class<V> responseType,
			Headers hdr, Object... uriVariables) {

		if (contentType.equals(MediaType.APPLICATION_XML_VALUE)
				&& (accept.equals(MediaType.TEXT_HTML_VALUE) || accept.equals(MediaType.APPLICATION_XML_VALUE))
				&& !prepared) {
			synchronized (this) {
				List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
				Jaxb2RootElementHttpMessageConverter jaxbMessageConverter = new Jaxb2RootElementHttpMessageConverter();
				List<MediaType> mediaTypes = new ArrayList<MediaType>();
				mediaTypes.add(MediaType.TEXT_HTML);
				mediaTypes.add(MediaType.APPLICATION_XML);
				jaxbMessageConverter.setSupportedMediaTypes(mediaTypes);
				messageConverters.add(jaxbMessageConverter);
				this.restTemplate.setMessageConverters(messageConverters);
				prepared = true;
			}

			HttpHeaders headers = new HttpHeaders();
			headers.addAll(this.headers);
			Optional.ofNullable(hdr)
					.ifPresent(hd -> hd.getHeaderNames().forEach(h -> headers.add(h, hdr.getHeader(h))));
		}
		ResponseEntity<V> responseEntity = uriVariables != null
				? this.restTemplate.exchange(url.toString(), method, new HttpEntity<K>(requestBody, headers),
						responseType, uriVariables)
				: this.restTemplate.exchange(url.toString(), method, new HttpEntity<K>(requestBody, headers),
						responseType);
		if (httpStatusCode != null && responseEntity.getStatusCodeValue() != httpStatusCode.value())
			throw new InvalidReturnedStatusCodeException(url.toString(), httpStatusCode.value());
		return responseEntity;
	}

}