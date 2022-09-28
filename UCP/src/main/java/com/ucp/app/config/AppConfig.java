package com.ucp.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ucp.app.log.filter.LoggingFilter;

@Configuration
public class AppConfig {

	@Bean
	FilterRegistrationBean<LoggingFilter> filterFilterRegistrationBean() {
		FilterRegistrationBean<LoggingFilter> loggingFilterBean = new FilterRegistrationBean<>(); 
		loggingFilterBean.addUrlPatterns("/api/*");
		loggingFilterBean.setFilter(new LoggingFilter());
		return loggingFilterBean;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    MappingJackson2HttpMessageConverter converter = 
	        new MappingJackson2HttpMessageConverter(mapper);
	    return converter;
	}

}
