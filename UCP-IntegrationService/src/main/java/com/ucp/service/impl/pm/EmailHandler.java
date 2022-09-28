package com.ucp.service.impl.pm;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ucp.api.config.API;
import com.ucp.api.config.DCTEmailConfig;
import com.ucp.service.base.ServiceRequestProcessor;
import com.ucp.service.impl.base.BaseServiceClientHandler;
import com.ucp.sp.model.AuthenticatedUser;

@Component("EmailHandler")
public class EmailHandler extends BaseServiceClientHandler<Object, Object> {

	private Logger LOGGER = LogManager.getLogger(getClass());

	@Autowired
	@Qualifier("dCTEmailConfig")
	protected DCTEmailConfig dctEmailConfig;
	
	@Override
	public void setAPI(API api, AuthenticatedUser user) {
		try {
			handle(user,api);
		} catch (Exception e) {
			LOGGER.error("Exception While sending data: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public <R> R getAPI(API api, AuthenticatedUser user) {
		try {
			return handle(user,api);
		} catch (Exception e) {
			LOGGER.error("Exception While fetching data: " + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	
	protected <I> void processRemParams(@SuppressWarnings("unchecked") I... input) throws Exception {
		@SuppressWarnings("unchecked")
		ServiceRequestProcessor ipProcessor = (Object... args) -> {
			try {
				this.api=(API) args[1];
				this.map = (args[2] != null)? (Map<String, Object>) args[2]: null;
				return true;
			} catch (ClassCastException | ArrayIndexOutOfBoundsException cce) {
				LOGGER.error("Exception While Processing Request Input:  " + cce.getMessage(), cce);
				throw new Exception("Exception While Processing Request Input: " + cce.getMessage());

			}
		};
		ipProcessor.processRequest(input);

		this.serviceURL = dctEmailConfig.getURI(this.api);
	}

	@Override
	public <R> R setAPI(Map<String, Object> map) {
		try {
			return handle(map.get("auth"),map.get("api"), map.get("email"));
		} catch (Exception e) {
			LOGGER.error("Exception While sending data: " + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
}
