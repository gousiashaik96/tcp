package com.ucp.config;

import com.ucp.api.config.API;
import com.ucp.api.config.APIConfig;

public abstract class BaseAPIConfig<T,R> implements APIConfig<T,R> {


	private String domain ; //= "https://integrations.loopworks.com";// PROD by Default
	private String baseURI; //= "/api/";
	private String version; // = "";

	
	public String getURI(API api, Object... params) throws Exception {
		Object[] result = ArrayUtil.appendtoArray(params, domain, baseURI, version);
		return resolveURL(api,result);
	}

	protected abstract String resolveURL(API api, Object... params)throws Exception;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

	@Override
	public String setConfig(Object... configs) throws Exception {
		return null;
	}

	/*
	 * @Override public R getSecurityToken(T param) throws Exception { return null;
	 * }
	 */
}
