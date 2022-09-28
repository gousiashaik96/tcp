package com.ucp.api.config.pm;

import org.springframework.stereotype.Component;

import com.ucp.api.config.API;
import com.ucp.api.config.BaseAPIConfig;
import com.ucp.api.config.BaseURLResolver;
import com.ucp.api.config.URLResolver;
import com.ucp.sp.model.AuthenticatedUser;
@Component("pmAPIConfig")
public class PmAPIConfig extends BaseAPIConfig<AuthenticatedUser,String>{ 	
	
	@SuppressWarnings("incomplete-switch")
	@Override
	protected String resolveURL(API api, Object... params) throws Exception {
		String apiUrl = "";
		switch (api) {
				case PM_SUCCESSPROFILE_DETAIL:
					apiUrl = getSuccessProfileDetails(api, params);
					break;
				default:
					apiUrl=getDefault_URL(api,params);
					break;
}
		return apiUrl;
	}
	
	private String getSuccessProfileDetails(API api, Object... params)
			throws Exception {
		URLResolver urlResolver = new BaseURLResolver() {
			@Override
			public String resolve(Object... params)
					throws Exception {
				String apiUrl = super.resolve(params);
				apiUrl += api.getSpId();
				return apiUrl;
			}
		};
		return urlResolver.resolve(params);
	}
	
	
	private String getDefault_URL(API api, Object... params) throws Exception {
		URLResolver urlResolver = new BaseURLResolver() {
			@Override
			public String resolve(Object... params)
					throws Exception {
				String apiUrl = super.resolveWithoutVersion(params);
				apiUrl += api.getUrl();
				return apiUrl;
			}
		};
		return urlResolver.resolve(params);
	}

	/*
	 * @Override public R getSecurityToken(T param) throws Exception { // TODO
	 * Auto-generated method stub return null; }
	 */

	}
