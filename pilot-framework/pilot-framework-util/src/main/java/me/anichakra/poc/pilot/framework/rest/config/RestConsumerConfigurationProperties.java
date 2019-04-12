package me.anichakra.poc.pilot.framework.rest.config;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class RestConsumerConfigurationProperties<T> {
	@NotNull
	private String name;
	@NotNull
	private URI url;
	private Boolean secured = false;
	private String contentType ="application/json";
	private String accept = "application/json";
	@NotNull
	private HttpMethod method;
	private HttpStatus statusCode;
    private Class<T> responseType;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> properties = new HashMap<>();
    
   
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}

	public Boolean getSecured() {
		return secured;
	}

	public void setSecured(Boolean secured) {
		this.secured = secured;
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
		return statusCode;
	}
	
	public void setStatusCode(Integer statusCode) {
		this.statusCode = HttpStatus.valueOf(statusCode);
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public Class<T> getResponseType() {
		return responseType;
	}

	public void setResponseType(Class<T> responseType) {
		this.responseType = responseType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
