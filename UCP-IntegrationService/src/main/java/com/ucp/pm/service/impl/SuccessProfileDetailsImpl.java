package com.ucp.pm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ucp.api.config.API;
import com.ucp.pm.service.SuccessProfileDetails;
import com.ucp.service.base.ServiceClientHandler;
import com.ucp.sp.model.AuthenticatedUser;
import com.ucp.sp.model.Profile;

@Component
public class SuccessProfileDetailsImpl implements SuccessProfileDetails {

	@Autowired
	@Qualifier("getSuccessProfileDetailsHandler")
	ServiceClientHandler successProfileDetailsHandler;

	@Override
	public Profile getSuccessProfileDetails(AuthenticatedUser user) {
		try {
			return successProfileDetailsHandler.getAPI(API.PM_SUCCESSPROFILE_DETAIL, user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
