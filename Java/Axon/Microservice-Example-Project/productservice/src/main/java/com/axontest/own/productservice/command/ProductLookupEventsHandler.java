package com.axontest.own.productservice.command;

import com.axontest.own.productservice.core.data.ProductLookupEntity;
import com.axontest.own.productservice.core.data.ProductLookupRepository;
import com.axontest.own.productservice.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// This eventhandler handles all the events regarding products
// (for LookUp purposes)
// but there can be multiple eventhandlers
// in SAGA these events can also be processed additionally
// every eventshandler must be a component -> has to be in the application context to work

/**
 * Event Processors are the components that take care of the technical aspects of that processing (eventhandlers).
 * They start a unit of work and possibly a transaction.
 * However, they also ensure that correlation data can be correctly attached to all
 * messages created during event processing, among other non-functional requirements.
 */

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    // repository to retrieve states of entities
    private final ProductLookupRepository productLookupRepository;

    // constructor based injection
    @Autowired
    public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is used to save core information of a
    // newly created ProductEntity in the LookUp-Table
    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(productCreatedEvent.getProductId(),
                productCreatedEvent.getTitle());

        this.productLookupRepository.save(productLookupEntity);
    }

    // This method will execute right before an events replay will be executed
    // here we prepare the db by deleting all existing values
    @ResetHandler
    public void reset() {
        this.productLookupRepository.deleteAll();
    }

}
