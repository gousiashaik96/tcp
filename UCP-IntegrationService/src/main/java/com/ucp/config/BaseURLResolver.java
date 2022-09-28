package com.ucp.config;

public class BaseURLResolver implements URLResolver{
	@Override
	public String resolve(Object... params) throws Exception {
		String domain = (String) params[0];
		String baseURI = (String) params[1];
		String version = (String) params[2];
		String prefix = domain + baseURI + version + "/";
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
