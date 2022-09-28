package com.ucp.service.base;

import java.util.Map;

import com.ucp.api.config.API;
import com.ucp.sp.model.AuthenticatedUser;

public interface ServiceClientHandler {
	public <I, R> R handle(@SuppressWarnings("unchecked") I... args)
			throws Exception;

	public void invokeService() throws Exception;

	public <I> void processRequest(@SuppressWarnings("unchecked") I... input)
			throws Exception;

	public <R> R processResponse() throws Exception;
	
	public void setAPI(API api,AuthenticatedUser user);

	public <R> R getAPI(API api,AuthenticatedUser user);
	
	public <R> R setAPI(Map<String, Object> map);
}
