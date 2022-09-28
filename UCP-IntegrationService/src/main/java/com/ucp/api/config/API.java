package com.ucp.api.config;

import org.springframework.http.HttpMethod;

public enum API {
	
	PM_SUCCESSPROFILE_DETAIL(""),EMAIL("");
	HttpMethod methodType = HttpMethod.GET;
	HttpMethod methodTypePost = HttpMethod.POST;
	String url;
	int spId;
	
	private API() {
	}


	private API(HttpMethod methodType, String url) {
		this.methodType = methodType;
		this.url = url;
	}

	private API(HttpMethod methodType) {
		this.methodType = methodType;
	}

	private API(String url) {
		this.url = url;
	}

	public HttpMethod getMethodType() {
		return methodType;
	}

	public void setMethodType(HttpMethod methodType) {
		this.methodType = methodType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public HttpMethod getMethodTypePost() {
		return methodTypePost;
	}


	public void setMethodTypePost(HttpMethod methodTypePost) {
		this.methodTypePost = methodTypePost;
	}


	public int getSpId() {
		return spId;
	}


	public void setSpId(int spId) {
		this.spId = spId;
	}

}
