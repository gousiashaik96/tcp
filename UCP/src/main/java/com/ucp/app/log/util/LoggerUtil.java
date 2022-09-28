package com.ucp.app.log.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.ucp.nosql.model.log.LogTransaction;

@Component
public class LoggerUtil {
	final Logger LOGGER = LogManager.getLogger(LoggerUtil.class);
	Gson gson = new Gson();
	@Autowired
	MongoTemplate logTemplate;

	public void addTransactionLog(LogTransaction log) {
		logTemplate.save(log);
		LOGGER.info(gson.toJson(log));
	}
}
