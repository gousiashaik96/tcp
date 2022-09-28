package com.ucp.service.impl.pm;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ucp.api.config.API;
import com.ucp.service.base.ServiceRequestProcessor;
import com.ucp.service.impl.base.BaseServiceClientHandler;
import com.ucp.sp.model.AuthenticatedUser;

@Component("getSuccessProfileDetailsHandler")
public class GetSuccessProfileDetailsHandler extends BaseServiceClientHandler<Profile, Profile> {



	
	/*
	 * public GetSuccessProfileDetailsHandler(API api) {
	 * super(API.PM_SUCCESSPROFILE_DETAIL); }
	 */
	 
	//@Override
	/*
	 * public void setAPI(API api,AuthenticatedUser user) { super.setAPI(api);
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Profile processResponse() throws Exception {
		if (isValidResponse()) {
			Profile profile = null;
			profile = response.getBody();
			response = null;
			return profile;

		}
		throw new Exception("Improper response for " + this.api);
	}

	protected <I> void processRemParams(@SuppressWarnings("unchecked") I... input) throws Exception {
		@SuppressWarnings("unchecked")
		ServiceRequestProcessor ipProcessor = (Object... args) -> {
			try {
				this.api=(API) args[1];
				return true;
			} catch (ClassCastException | ArrayIndexOutOfBoundsException cce) {
				throw new Exception("Exception While Processing Request Input " + cce.getMessage());
			}
		};
		ipProcessor.processRequest(input);

		this.serviceURL = PmAPIConfig.getURI(this.api);
	}

	@Override
	public void setAPI(API api, AuthenticatedUser user) {
		try {
			handle(user,api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public <R> R getAPI(API api, AuthenticatedUser user) {
		try {
			return handle(user,api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <R> R setAPI(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
