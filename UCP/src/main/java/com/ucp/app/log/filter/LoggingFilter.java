package com.ucp.app.log.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ucp.app.log.util.LoggerUtil;
import com.ucp.app.request.RequestWrapper;
import com.ucp.app.response.ResponseWrapper;
import com.ucp.nosql.model.log.LogTransaction;

public class LoggingFilter implements Filter {
	private static final Logger LOGGER = LogManager.getLogger(LoggingFilter.class);

	private ApplicationContext appContext;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
		appContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		LogTransaction log = new LogTransaction();
		UUID uuid = UUID.randomUUID();
		log.setTransactionId(uuid.toString());
		RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
		requestWrapper.addHeader("transactionId", log.getTransactionId());
		LOGGER.info("The logging filter " + log.getTransactionId());
		logHttpRequest(log, requestWrapper, responseWrapper);
		chain.doFilter(requestWrapper, responseWrapper);
		logHttpResponse(log, requestWrapper, responseWrapper);
	}

	private void logHttpRequest(LogTransaction log, RequestWrapper requestWrapper, ResponseWrapper responseWrapper) {
		long start = System.currentTimeMillis();
		log.setTransactionType("Internal");
		log.setRequestTimeStamp(start);
		log.setURL(requestWrapper.getRequestURL().toString());
		log.setMethod(requestWrapper.getMethod());
		if (requestWrapper.getMethod().equalsIgnoreCase("POST") || requestWrapper.getMethod().equalsIgnoreCase("PUT")) {
			log.setRequestData(requestWrapper.getBody());
		}
		Map<String, String> headers = Collections.list(requestWrapper.getHeaderNames())
			    .stream()
			    .collect(Collectors.toMap(h -> h, requestWrapper::getHeader));
		log.setHeaders(headers);
	}

	private void logHttpResponse(LogTransaction log, RequestWrapper requestWrapper, ResponseWrapper responseWrapper) {
		String responseBody = null;
		if (null != log) {
			long end = System.currentTimeMillis();
			responseBody = responseWrapper.getBody();
			LOGGER.info("Response " + responseBody);
			log.setResponseData(responseBody);
			log.setStatusCode(responseWrapper.getStatus());
			log.setResponseTimeStamp(end);
			log.setDuration(log.getResponseTimeStamp() - log.getRequestTimeStamp());
			LOGGER.info("response Transaction Id " + requestWrapper.getHeader("transactionId"));
			LOGGER.info("the Log " + log);
			LoggerUtil loggerUtil = appContext.getBean(LoggerUtil.class);
			loggerUtil.addTransactionLog(log);
		}
	}
	
}
