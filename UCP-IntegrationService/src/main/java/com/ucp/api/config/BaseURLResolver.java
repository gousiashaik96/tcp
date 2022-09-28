package com.ucp.api.config;

public class BaseURLResolver implements URLResolver{
	@Override
	public String resolve(Object... params) throws Exception {
		String domain = (String) params[0];
		String version = (String) params[1];
		String baseURI = (String) params[2];
		String prefix = domain + "/" + version + baseURI ;
		return prefix;
	}
	
	@Override
	public String resolveWithoutVersion(Object... params) throws Exception {
		String domain = (String) params[0];
		String baseURI = (String) params[1];
		String prefix = domain + baseURI;
		return prefix;
	}
}
