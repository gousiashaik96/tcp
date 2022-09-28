package com.ucp.service.impl.pm;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ucp.api.config.API;
import com.ucp.service.base.ServiceRequestProcessor;
import com.ucp.service.impl.base.BaseServiceClientHandler;
import com.ucp.sp.model.AuthenticatedUser;
import com.ucp.sp.model.Profile;

@Component("GetSuccessProfileDetailsByIdHandler")
public class GetSuccessProfileDetailsByIdHandler extends BaseServiceClientHandler<Profile, Profile> {

	private Logger LOGGER = LogManager.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Profile processResponse() throws Exception {
		if (isValidResponse()) {
			Profile profile = null;
			profile = response.getBody();
			response = null;
			return profile;

		}
		LOGGER.error("Improper response for: " + this.api, this.api);
		throw new Exception("Improper response for: " + this.api);
	}

	protected <I> void processRemParams(@SuppressWarnings("unchecked") I... input) throws Exception {
		@SuppressWarnings("unchecked")
		ServiceRequestProcessor ipProcessor = (Object... args) -> {
			try {
				this.api=(API) args[1];
				return true;
			} catch (ClassCastException | ArrayIndexOutOfBoundsException cce) {
				LOGGER.error("Exception While Processing Request Input:  " + cce.getMessage(), cce);
				throw new Exception("Exception While Processing Request Input: " + cce.getMessage());

			}
		};
		ipProcessor.processRequest(input);

		this.serviceURL = PmAPIConfig.getURI(this.api);
	}

	@Override
	public void setAPI(API api, AuthenticatedUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <R> R getAPI(API api, AuthenticatedUser user) {
		try {
			return handle(user,api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Exception While fetching data: " + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <R> R setAPI(Map<String, Object> map) {
		return null;
	}

	

}
