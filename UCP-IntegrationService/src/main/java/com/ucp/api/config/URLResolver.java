package com.ucp.api.config;

public interface URLResolver {
	public String resolve(Object... params) throws Exception;
	
	public String resolveWithoutVersion(Object... params) throws Exception;
	
}
