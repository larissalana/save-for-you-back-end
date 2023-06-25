package com.saveforyou.corebankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableJpaAuditing
@EnableDiscoveryClient
public class CoreBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreBankServiceApplication.class, args);
	}

}
