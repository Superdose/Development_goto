package com.axontest.own.userservice.query;

import com.axontest.own.core.model.PaymentDetails;
import com.axontest.own.core.model.User;
import com.axontest.own.core.query.FetchUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

// This eventhandler handles all the events regarding payments
// but there can be multiple eventhandlers
// in SAGA these events can also be processed additionally
// every eventshandler must be a component -> has to be in the application context to work
@Component
public class UserEventsHandler {

    // this is an queryhandler
    // by styleguide, queryhandler-methods have the name "on"
    // the method-argument is the query to handle / to catch
    // this queryhandler is used to retrieve (hardcoded) UserPaymentDetails
    @QueryHandler
    public User on(FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("SERGEY KARGOPOLOV")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        return User.builder()
                .firstName("Sergey")
                .lastName("Kargopolov")
                .userId(fetchUserPaymentDetailsQuery.getUserid())
                .paymentDetails(paymentDetails)
                .build();
    }

}
