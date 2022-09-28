package com.ucp.config;


import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;


@Component
public class YAMLLoader {	

	
	
	@SuppressWarnings("unchecked")
	public Map<String, String> loadProperties(String currentEnvironment) throws Exception{
		InputStream ism = null;
		try {

			Yaml yaml = new Yaml();

			ism = getClass().getClassLoader().getResourceAsStream("ThirdPartyConfig.yml");

			Map<String, Map<String, Map<String, Map<String, String>>>> yamlFileValues = (Map<String, Map<String, Map<String, Map<String, String>>>>) yaml
					.load(ism);

			Map<String, Map<String, String>> secondLevelValues = null;

			for (String key : yamlFileValues.keySet()) {
				Map<String, Map<String, Map<String, String>>> firstLevelValues = yamlFileValues.get(key);
				for (String subValueKey : firstLevelValues.keySet()) {
					secondLevelValues = firstLevelValues.get(subValueKey);
				}
			}

			Map<String, String> thirdLevelValues = secondLevelValues.get(currentEnvironment);

			return thirdLevelValues;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			ism.close();
		}
		return null;
	}
	
	/*String convertYamlToJson(String yaml) throws JsonProcessingException {

	    ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
	    Object obj = null;
		try {
			obj = yamlReader.readValue(yaml, ThirdPartyConfigPOJO.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    ObjectMapper jsonWriter = new ObjectMapper();
	    return jsonWriter.writeValueAsString(obj);
	}*/

	
	/*public static void main(String[] args) throws FileNotFoundException {
		
		YAMLLoader ymlLoader = new YAMLLoader();
		ymlLoader.loadProperties("QA");
		
	}*/

}
