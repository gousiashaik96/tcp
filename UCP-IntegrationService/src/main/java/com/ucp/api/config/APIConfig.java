package com.ucp.api.config;


public interface APIConfig<T,R> {
public String getURI(API api,Object...params) throws Exception;
	
	public String setConfig(Object...configs) throws Exception;

	//public R getSecurityToken(T param) throws Exception;
}
