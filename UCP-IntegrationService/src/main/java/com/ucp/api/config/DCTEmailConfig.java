package com.ucp.api.config;

import org.springframework.stereotype.Component;

import com.ucp.sp.model.AuthenticatedUser;

@Component("dCTEmailConfig")
public class DCTEmailConfig extends BaseAPIConfig<AuthenticatedUser,String>{

	@SuppressWarnings("incomplete-switch")
	@Override
	protected String resolveURL(API api, Object... params) throws Exception {
		String apiUrl = "";
		switch (api) {
				case EMAIL:
					apiUrl = getEmailDetails(api, params);
					break;
				default:
					apiUrl = "";
					break;
}
		return apiUrl;
	}
	
	private String getEmailDetails(API api, Object... params)
			throws Exception {
		URLResolver urlResolver = new BaseURLResolver() {
			@Override
			public String resolve(Object... params)
					throws Exception {
				String apiUrl = super.resolve(params);
				return apiUrl;
			}
		};
		return urlResolver.resolve(params);
	}
}
