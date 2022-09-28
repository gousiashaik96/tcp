package com.ucp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import com.ucp.api.config.CommonModelConfig;
import com.ucp.api.config.DCTEmailConfig;
import com.ucp.api.config.pm.PmAPIConfig;
import com.ucp.config.YAML_KEY;
import com.ucp.model.yml.Configuration;
import com.ucp.model.yml.DCTEmailEnvConfig;
import com.ucp.model.yml.Environments;
import com.ucp.model.yml.PmEnvConfig;

@Component
public class EnvironmentConfigManager {

	
	@Override
	public String toString() {
		return "EnvironmentConfigManager [pmConfig=" + pmConfig + "]";
	}

	@Autowired
	@Qualifier("pmAPIConfig")
	protected PmAPIConfig pmAPIConfig;

	@Autowired
	@Qualifier("dCTEmailConfig")
	protected DCTEmailConfig dctEmailConfig;
	
		private String currentEnvironment;

	public String getPmConfig() {
			return pmConfig;
		}

		public void setPmConfig(String pmConfig) {
			this.pmConfig = pmConfig;
		}

	private String pmConfig;
	

	@Autowired
	CommonModelConfig modelConfig;
	public String getCurrentEnvironment() {
		return currentEnvironment;
	}

	public void setCurrentEnvironment(String currentEnvironment) {
		this.currentEnvironment = currentEnvironment;
	}

	@SuppressWarnings("unchecked")
	public void initializeProperties() throws ServiceException, IOException {
		
		
		InputStream ism = null;
		try {
			//File file=ResourceUtils.getFile("classpath:ThirdPartyConfig.yml");
			//  ism = new FileInputStream(file);
			Yaml yaml = new Yaml();
			ism = getClass().getClassLoader().getResourceAsStream("ThirdPartyConfig.yml");
			Configuration config = yaml.loadAs(ism, Configuration.class);
			setThirdPartyConfigs(config, YAML_KEY.getThirdPartys(), currentEnvironment);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ism.close();
		}
	}

	private void setThirdPartyConfigs(Configuration config, YAML_KEY[] thirdPartyAPIs, String currentEnvironment) {
		for (int i = 0; i < thirdPartyAPIs.length; i++) {
			Environments envs = config.getEnvironments();
			switch (thirdPartyAPIs[i]) {
			case IILEVEL_PM:
				PmEnvConfig pmConf = null;
				if (currentEnvironment.equalsIgnoreCase("local")) {
					pmConf = envs.getPm().getLocal();
				}else if (currentEnvironment.equalsIgnoreCase("dev") || currentEnvironment.equalsIgnoreCase("dev")) {
					pmConf = envs.getPm().getDev();
				} else if (currentEnvironment.equalsIgnoreCase("prod")
						|| currentEnvironment.equalsIgnoreCase("production")) {
					pmConf = envs.getPm().getProd();
				} else if (currentEnvironment.equalsIgnoreCase("eu")) {
					pmConf = envs.getPm().getProd();
				} else if (currentEnvironment.equalsIgnoreCase("eudev")) {
					pmConf = envs.getPm().getEudev();
				} else if (currentEnvironment.equalsIgnoreCase("euqa")) {
					pmConf = envs.getPm().getEuqa();
				} else if (currentEnvironment.equalsIgnoreCase("eustage")) {
					pmConf = envs.getPm().getEustage();
				} else if (currentEnvironment.equalsIgnoreCase("euprod")
						|| currentEnvironment.equalsIgnoreCase("eu-prod")) {
					pmConf = envs.getPm().getEuprod();
				}
				setPmConfig(pmConf);
				break;
			case DCTEMAIL:
				DCTEmailEnvConfig dctEmailEnvConf = null;
				if (currentEnvironment.equalsIgnoreCase("local")) {
					dctEmailEnvConf = envs.getDctEmail().getLocal();
				}else if (currentEnvironment.equalsIgnoreCase("dev")) {
					dctEmailEnvConf = envs.getDctEmail().getDev();
				}else if (currentEnvironment.equalsIgnoreCase("qa")) {
					dctEmailEnvConf = envs.getDctEmail().getQa();
				}else if (currentEnvironment.equalsIgnoreCase("stage")) {
					dctEmailEnvConf = envs.getDctEmail().getStage();
				} else if (currentEnvironment.equalsIgnoreCase("produs")
						|| currentEnvironment.equalsIgnoreCase("production")) {
					dctEmailEnvConf = envs.getDctEmail().getProdus();
				} else if (currentEnvironment.equalsIgnoreCase("prodeu")) {
					dctEmailEnvConf = envs.getDctEmail().getProdeu();
				} else if (currentEnvironment.equalsIgnoreCase("prodcn")) {
					dctEmailEnvConf = envs.getDctEmail().getProdcn();
				} 
				setDctEmailConfig(dctEmailEnvConf);
				break;
			default:
				break;
			}
		}
	}

	private void setPmConfig(PmEnvConfig pmConf) {
		// Setting Generic Model Config for Loop
		modelConfig.setPmConfig(pmConf);

		pmAPIConfig.setDomain(pmConf.getDomain());
		pmAPIConfig.setBaseURI(pmConf.getBaseURI());
		pmAPIConfig.setVersion(pmConf.getVersion());
		//pmAPIConfig.setAuthToken(pmConf.getAuthorization());
		//pmAPIConfig.setPmIdprovider(pmConf.getPmIdprovider());
	}
	
	private void setDctEmailConfig(DCTEmailEnvConfig dctEmailEnvConfig) {
		// Setting Generic Model Config for Loop
		modelConfig.setDctEmailEnvConfig(dctEmailEnvConfig);
		dctEmailConfig.setDomain(dctEmailEnvConfig.getDomain());
		dctEmailConfig.setBaseURI(dctEmailEnvConfig.getBaseURI());
		dctEmailConfig.setVersion(dctEmailEnvConfig.getVersion());
	}
}
