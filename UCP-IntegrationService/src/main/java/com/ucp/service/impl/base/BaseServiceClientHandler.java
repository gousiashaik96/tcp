package com.ucp.service.impl.base;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucp.api.config.pm.PmAPIConfig;
import com.ucp.api.config.DCTEmailConfig;
import com.ucp.exception.ISUnauthorizedException;
import com.ucp.service.base.ServiceRequestProcessor;
import com.ucp.sp.model.AuthenticatedUser;

public abstract class BaseServiceClientHandler<T, R> extends AbsServiceClientHandler {
	
	private Logger LOGGER = LogManager.getLogger(getClass());
	
	@Autowired
	RestTemplate restTemplate;

	protected ResponseEntity<T> response = null;

	@Autowired
	@Qualifier("pmAPIConfig")
	protected PmAPIConfig PmAPIConfig;
	
	protected AuthenticatedUser authUser = null;
	protected Map<String, Object> map = new HashMap<String, Object>();

	/*
	 * public BaseServiceClientHandler(API api) { super(); this.api = api; }
	 * 
	 */

	@Override
	public <I> void processRequest(@SuppressWarnings("unchecked") I... input) throws Exception {
		// AuthenticatedUser authUser
		ServiceRequestProcessor ipProcessor = (Object... args) -> {
			try {
				this.authUser = (AuthenticatedUser) args[0];
				processRemParams(args);
				return true;
			} catch (Exception ex) {
				LOGGER.error("Error while processing request: " + ex.getMessage(), ex);
				throw new Exception(ex.getMessage());
			}
		};
		ipProcessor.processRequest(input);
	}

	protected <I> void processRemParams(@SuppressWarnings("unchecked") I... input) throws Exception {
		@SuppressWarnings("unchecked")
		ServiceRequestProcessor ipProcessor = (Object... args) -> {
			try {
				this.map = (Map<String, Object>) args[2];
				return true;
			} catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
				LOGGER.error("Exception While Processing Request Input:  " + ex.getMessage(), ex);
				throw new Exception(ex.getMessage());
			}
		};
		ipProcessor.processRequest(input);
		this.serviceURL = PmAPIConfig.getURI(this.api);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void invokeService() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		try {
			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(map, buildHttpEntity());
			String jsonstr = mapper.writeValueAsString(map);
			LOGGER.info("jsonstr==>" + jsonstr);
			Class<T> clazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0]);

			response = (ResponseEntity<T>) restTemplate.exchange(this.serviceURL, this.api.getMethodType(),
					requestEntity, clazz);

		}catch (HttpClientErrorException.Unauthorized ex) {
			throw new ISUnauthorizedException("Authtoken is not valid");
		}catch (Exception ex) {
			LOGGER.error("Exception While invoking API URL==>" + this.serviceURL, ex);
			throw new Exception("Exception While invoking API: " + ex.getMessage());
		}
	}

	protected HttpHeaders buildHttpEntity() throws Exception {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("authToken", authUser.getAuthToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R processResponse() throws Exception {
		if (isValidResponse()) {
			return (R) response.getBody();
		}
		LOGGER.error("Improper response for " + this.api);
		throw new Exception("Improper response for " + this.api);
	}

	public boolean isValidResponse() throws Exception {
		try {
			return response != null && response.getStatusCode().name().equalsIgnoreCase("OK");

		} catch (Exception ex) {
			LOGGER.error("Invalid response: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
	}

}
