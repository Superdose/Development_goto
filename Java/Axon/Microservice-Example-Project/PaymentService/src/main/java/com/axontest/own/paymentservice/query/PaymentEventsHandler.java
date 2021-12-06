package com.axontest.own.paymentservice.query;

import com.axontest.own.core.events.PaymentProcessedEvent;
import com.axontest.own.paymentservice.data.PaymentEntity;
import com.axontest.own.paymentservice.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// This eventhandler handles all the events regarding payments
// but there can be multiple eventhandlers
// in SAGA these events can also be processed additionally
// every eventshandler must be a component -> has to be in the application context to work
@Component
public class PaymentEventsHandler {

    // repository to update states of entities
    private final PaymentRepository paymentRepository;

    // constructor based injection
    @Autowired
    public PaymentEventsHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is used to create a new PaymentEntity
    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setPaymentId(paymentProcessedEvent.getPaymentId());
        paymentEntity.setOrderId(paymentProcessedEvent.getOrderId());

        this.paymentRepository.save(paymentEntity);
    }

}
