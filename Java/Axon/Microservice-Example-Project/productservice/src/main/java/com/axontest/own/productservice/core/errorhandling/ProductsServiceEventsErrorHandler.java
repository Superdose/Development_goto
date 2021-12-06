package com.axontest.own.productservice.core.errorhandling;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

// this ListenerInvocationErrorHandler will catch errors, that occur in an EventHandler or
// are thrown outside an Eventhandler
// we use this ListenerInvocationErrorHandler to catch Eventhandler-exceptions and throw them out again,
// so they will be caught by the ?Controller (or rather ControllerAdvice?)
public class ProductsServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
// this ListenerInvocationErrorHandler must also be registered for the event processing group
// this will be done in the main method (Spring.run)
// --> public void configure(EventProcessingConfigurer config)

    @Override
    public void onError(Exception e, EventMessage<?> eventMessage, EventMessageHandler eventMessageHandler) throws Exception {
        //  log the message

        // rethrow the exception further out -> last rethrow to make the current transaction rollback
        throw e;
    }
}
