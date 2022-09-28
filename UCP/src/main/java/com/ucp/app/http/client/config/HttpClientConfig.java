package com.ucp.app.http.client.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.ucp.app.log.util.LoggerUtil;
import com.ucp.nosql.model.log.LogTransaction;

@Configuration
@EnableScheduling
public class HttpClientConfig {
	private Logger LOGGER = LogManager.getLogger(getClass());

	private static final int CONNECT_TIMEOUT = 30 * 1000;
	private static final int REQUEST_TIMEOUT = 30 * 1000;
	private static final int SOCKET_TIMEOUT = 60 * 1000;
	private static final int MAX_TOTAL_CONNECTIONS = 100;
	private static final int MAX_ROUTE_PER_HOST = 50;
	private static final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 20 * 1000;
	private static final int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS = 30;

	Gson gson = new Gson();

	@Autowired
	private WebApplicationContext webApplicationContext;

	private ApplicationContext appContext;

	Map<String, Object> transactionLog = new ConcurrentHashMap<>();

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient());
		return clientHttpRequestFactory;
	}

	@Bean
	public CloseableHttpClient httpClient() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUEST_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();

		return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingConnectionManager()).setKeepAliveStrategy(connectionKeepAliveStrategy())
				.setSSLHostnameVerifier(new NoopHostnameVerifier()).addInterceptorFirst(new HttpRequestInterceptor() {
					@Override
					public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
						// TODO Auto-generated method stub
						String method = request.getRequestLine().getMethod();
						String url = request.getRequestLine().getUri();
						Map<String, String> headers = Arrays.asList(request.getAllHeaders()).stream()
								.filter(header -> null != header && StringUtils.isNotEmpty(header.getValue()))
								.collect(Collectors.toMap(h -> h.getName(), h -> h.getValue()));
						String requestBody = null;
						if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
							HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
							InputStream inputStream = entity.getContent();
							requestBody = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
						}
						logRequest(context, url, headers, method, requestBody);
					}
				}).addInterceptorLast(new HttpResponseInterceptor() {
					@Override
					public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
						// TODO Auto-generated method stub
						HttpEntity entity = response.getEntity();
						// read the body of the response
						String body = EntityUtils.toString(entity);
						// now replace it
						HttpEntity newEntity = new StringEntity(body, ContentType.get(entity));
						response.setEntity(newEntity);
						String transactionId = (String) context.getAttribute("transactionId");
						logResponse(transactionId, body, response.getStatusLine().getStatusCode());

					}
				}).build();
	}

	@Bean
	public PoolingHttpClientConnectionManager poolingConnectionManager() {
		SSLContextBuilder builder = new SSLContextBuilder();
		try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		} catch (NoSuchAlgorithmException | KeyStoreException e) {
			LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
		}

		SSLConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(builder.build());
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		poolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_PER_HOST);
		return poolingConnectionManager;
	}

	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();

					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
			}
		};
	}

	@Bean
	public Runnable idleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager) {
		return new Runnable() {
			@Override
			@Scheduled(fixedDelay = 10000)
			public void run() {
				try {
					if (connectionManager != null) {
						LOGGER.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
						connectionManager.closeExpiredConnections();
						connectionManager.closeIdleConnections(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);
					} else {
						LOGGER.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
					}
				} catch (Exception e) {
					LOGGER.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
				}
			}
		};
	}

	private void logRequest(HttpContext context, String url, Map<String, String> headers, String method,
			String requestBody) {
		UUID uuid = UUID.randomUUID();
		LogTransaction log = new LogTransaction();
		log.setTransactionId(uuid.toString());
		log.setTransactionType("External");
		log.setRequestTimeStamp(new Date().getTime());
		log.setURL(url);
		/*
		 * if (null != headers && headers.containsKey("transactionId")) {
		 * log.setTransactionRefId(headers.get("transactionId")); }
		 */
		log.setTransactionRefId(getTransactionRefId());
		log.setHeaders(headers);
		log.setMethod(method);
		if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
			log.setRequestData(requestBody);
		}
		context.setAttribute("transactionId", log.getTransactionId());
		transactionLog.put(log.getTransactionId(), log);
	}

	private void logResponse(String transactionId, String responseBody, int statusCode) {
		appContext = WebApplicationContextUtils
				.getWebApplicationContext(webApplicationContext.getServletContext());
		LoggerUtil loggerUtil = appContext.getBean(LoggerUtil.class);
		if (StringUtils.isNotEmpty(transactionId) && transactionLog.containsKey(transactionId)) {
			LogTransaction log = (LogTransaction) transactionLog.get(transactionId);
			log.setResponseData(responseBody);
			log.setStatusCode(statusCode);
			log.setResponseTimeStamp(new Date().getTime());
			log.setDuration(log.getResponseTimeStamp() - log.getRequestTimeStamp());
			loggerUtil.addTransactionLog(log);
			transactionLog.remove(transactionId);
		}
	}
	
	private String getTransactionRefId() {
		String transactionRefId = null;
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		if (RequestContextHolder.getRequestAttributes() != null) {
		    HttpServletRequest httpServletRequest = ((ServletRequestAttributes) attributes).getRequest();
		    if (null != httpServletRequest && null != httpServletRequest.getHeader("transactionId")) {
				transactionRefId = httpServletRequest.getHeader("transactionId");
			}
		}return transactionRefId;
	}

}
