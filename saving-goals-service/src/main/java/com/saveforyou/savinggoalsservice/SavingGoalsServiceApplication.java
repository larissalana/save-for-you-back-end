package com.saveforyou.savinggoalsservice;

import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.impl.CustomMongoRepositoryImpl;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@EnableMongock
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.saveforyou.savinggoalsservice.infrastructure.mongo.repository", repositoryBaseClass = CustomMongoRepositoryImpl.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SavingGoalsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingGoalsServiceApplication.class, args);
	}
}
