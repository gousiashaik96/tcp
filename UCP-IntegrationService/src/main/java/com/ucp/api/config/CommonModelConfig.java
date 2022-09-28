package com.ucp.api.config;

import org.springframework.stereotype.Component;

import com.ucp.model.yml.DCTEmailEnvConfig;
import com.ucp.model.yml.PmEnvConfig;

@Component
public class CommonModelConfig {
	
	private PmEnvConfig pmConfig;
	private DCTEmailEnvConfig dctEmailEnvConfig;
	public PmEnvConfig getLoopConfig() {
		return pmConfig;
	}

	public void setPmConfig(PmEnvConfig pmConfig) {
		this.pmConfig = pmConfig;
	}

	public DCTEmailEnvConfig getDctEmailEnvConfig() {
		return dctEmailEnvConfig;
	}

	public void setDctEmailEnvConfig(DCTEmailEnvConfig dctEmailEnvConfig) {
		this.dctEmailEnvConfig = dctEmailEnvConfig;
	}

	
	
}
