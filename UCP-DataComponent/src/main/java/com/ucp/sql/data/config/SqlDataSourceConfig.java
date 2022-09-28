package com.ucp.sql.data.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"com.ucp.data.repo.dao" })
public class SqlDataSourceConfig {
	@Primary
	@Bean(name = "DataSourceProperties")
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties sqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean(name = "sqlDataSource")
	@ConfigurationProperties("spring.datasource")
	public DataSource sqlDataSource(
			@Qualifier("DataSourceProperties") DataSourceProperties dataSourceProperties) {
		return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder,
			@Qualifier("sqlDataSource") DataSource sqlDataSource) {
		Map<String, String> primaryJpaProperties = new HashMap<>();
		primaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		primaryJpaProperties.put("hibernate.hbm2ddl.auto", "none");
		return entityManagerFactoryBuilder.dataSource(sqlDataSource).packages("com.ucp.data.model.entity")
				.persistenceUnit("sqlDataSource").properties(primaryJpaProperties).build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
