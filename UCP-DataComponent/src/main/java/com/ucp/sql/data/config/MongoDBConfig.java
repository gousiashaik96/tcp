package com.ucp.sql.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoDBConfig {
	@Value("${spring.data.mongodb.uri}")
	String mongoDBUri;
	
	@Value("${spring.data.mongodb.database}")
	String database;

	@Bean
	public MongoClient ucpMongoConfig() {
		ConnectionString connectionString = new ConnectionString(mongoDBUri);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.build();
		return MongoClients.create(mongoClientSettings);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(ucpMongoConfig(), database);
	}

}
