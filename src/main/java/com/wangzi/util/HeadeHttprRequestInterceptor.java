package com.wangzi.util;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

public class HeadeHttprRequestInterceptor implements ClientHttpRequestInterceptor{
	
	private String key;
	private String value;
	
	public HeadeHttprRequestInterceptor(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
		requestWrapper.getHeaders().set(key, value);
		return execution.execute(requestWrapper, body);
	}

}
