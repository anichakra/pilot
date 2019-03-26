package me.anichakra.poc.pilot.framework.rest.config;

import java.net.URI;

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

	public void setContenttype(String contentType) {
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

}
