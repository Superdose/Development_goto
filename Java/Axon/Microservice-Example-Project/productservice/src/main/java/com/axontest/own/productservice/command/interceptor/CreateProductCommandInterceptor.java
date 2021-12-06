package com.axontest.own.productservice.command.interceptor;

import com.axontest.own.productservice.command.CreateProductCommand;
import com.axontest.own.productservice.core.data.ProductLookupEntity;
import com.axontest.own.productservice.core.data.ProductLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

// A MessageDispatchInterceptor needs the @Component annotation
// This interceptor will intercept all commands.
@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
// Creating an MessageDispatchInterceptor is not enough. It has to be registered.
// This is done in the main method (Spring.run).

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    // repository to retrieve states of entities
    private final ProductLookupRepository productLookupRepository;

    // constructor-based injection
    @Autowired
    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    // the interceptor method, which is called whenever a command is send
    // this interceptor is used to look, before a new ProductEntity is created,
    // if a replica of that entity already exists
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {

            // Room for validation or logging for example
            CreateProductCommandInterceptor.LOGGER.info("Intercepted command: " + command.getPayloadType());

            /**
             * This interceptor will intercept all commands.
             * However, to check if the incoming command is the type of command we want to intercept,
             * we use an if statement.
             * It looks like this:
             * if (CreateProductCommand.class.equals(command.getPayloadType()))
              */

            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                // check if the entity already exists via the LookUp-Table
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                ProductLookupEntity productLookupEntity = this.productLookupRepository.findByProductIdOrTitle(
                        createProductCommand.getProductId(),
                        createProductCommand.getTitle());

                // if such an entity has already been persisted, throw an error
                if (productLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Product with productId %s or title %s already exits",
                                    createProductCommand.getProductId(),
                                    createProductCommand.getTitle())
                    );
                }
            }

            return command;
        };
    }
}
