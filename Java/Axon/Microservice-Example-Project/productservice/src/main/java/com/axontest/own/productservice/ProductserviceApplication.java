package com.axontest.own.productservice;

import com.axontest.own.productservice.command.interceptor.CreateProductCommandInterceptor;
import com.axontest.own.productservice.core.errorhandling.ProductsServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

// EnableDiscoveryClient -> Spring will try to register this service at an EurekaDiscoveryServer
@EnableDiscoveryClient
@SpringBootApplication
public class ProductserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}

	// Registering the CommandInterceptor (here: CreateProductCommandInterceptor)
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext applicationContext,
														CommandBus commandBus) {
		// get the CreateProductCommandInterceptor bean and register it in the commandBus
		// Both the ApplicationContext and CommandBus we get via autowired
		commandBus.registerDispatchInterceptor(
				applicationContext.getBean(CreateProductCommandInterceptor.class));
	}

	// registering the ListenerInvocationErrorHandler
	@Autowired
	public void configure(EventProcessingConfigurer config) {

		// registering the ListenerInvocationErrorHandler
		// first argument (String) is the ProcessingGroup
		// second argument is a function, in which the ListenerInvocationErrorHandler
		// is instantiated
		config.registerListenerInvocationErrorHandler("product-group",
				configuration -> new ProductsServiceEventsErrorHandler());

		// if we want to use the default ListenerInvocationErrorHandler provided by axon,
		// we do almost the same as if we were the register our own ListenerInvocationErrorHandler
//		config.registerListenerInvocationErrorHandler("product-group",
//				configuration -> PropagatingErrorHandler.instance());
	}

	// we need to give this SnapshotTriggerDefinition Bean a specific name, so we can reference that name when
	// applying the SnapshotTriggerDefinition onto an Aggregate
	@Bean(name = "productSnapshotTriggerDefinition")
	public SnapshotTriggerDefinition productSnapshotTriggerDefinition(Snapshotter snapshotter) {
		// EventCountSnapshotTriggerDefinition will make a Snapshot, if the event count hits X events
		return new EventCountSnapshotTriggerDefinition(snapshotter, 3);
	}

}
