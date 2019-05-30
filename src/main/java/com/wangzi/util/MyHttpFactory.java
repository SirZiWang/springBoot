package com.wangzi.util;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class MyHttpFactory extends SimpleClientHttpRequestFactory{
	
	public MyHttpFactory() {
		super();
		setConnectTimeout(5 * 1000);
		setReadTimeout(5 * 1000);
	}
}
