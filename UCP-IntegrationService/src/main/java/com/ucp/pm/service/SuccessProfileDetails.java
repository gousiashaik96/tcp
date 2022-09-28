package com.ucp.pm.service;

import com.ucp.sp.model.AuthenticatedUser;
import com.ucp.sp.model.Profile;

public interface SuccessProfileDetails {

	public Profile getSuccessProfileDetails(AuthenticatedUser user);

}
