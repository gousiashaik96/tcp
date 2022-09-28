package com.ucp.service.impl.base;

import com.ucp.service.base.ServiceClientHandler;

public abstract class AbsServiceClientHandler implements ServiceClientHandler {

	
	protected com.ucp.api.config.API api = null;
	protected String serviceURL = null;
	public AbsServiceClientHandler() {
		
	}
	@Override
	public <I, R> R handle(I... args) throws Exception {
try {
			
			processRequest(args);
			invokeService();
			@SuppressWarnings("unchecked")
			R procRet = (R) processResponse();
			return procRet;
		} catch (Exception ex) {
			
			throw new Exception(ex.getMessage());
		}
	}



}
