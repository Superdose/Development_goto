package com.axontest.own.ordersservice2;

import org.axonframework.config.Configuration;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

// EnableDiscoveryClient -> Spring will try to register this service at an EurekaDiscoveryServer
@EnableDiscoveryClient
@SpringBootApplication
public class OrdersService2Application {

	public static void main(String[] args) {
		SpringApplication.run(OrdersService2Application.class, args);
	}

	// create a DeadLineManager and add it to the ApplicationContext
	// this way we can use the DeadLineManager across the application
	@Bean
	public DeadlineManager deadlineManager(Configuration configuration,
										   SpringTransactionManager transactionManager) {
		return SimpleDeadlineManager.builder()
				.scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
				.transactionManager(transactionManager)
				.build();
	}
}
