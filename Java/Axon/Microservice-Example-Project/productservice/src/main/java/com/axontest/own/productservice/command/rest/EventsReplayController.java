package com.axontest.own.productservice.command.rest;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// this RestController is used as an endpoint to perform an event replay
@RestController
@RequestMapping("/management")
public class EventsReplayController {

    // we will use the EventProcessingConfiguration to get the TrackingEventProcessor
    private final EventProcessingConfiguration eventProcessingConfiguration;

    // constructor-based injection
    @Autowired
    public EventsReplayController(EventProcessingConfiguration eventProcessingConfiguration) {
        this.eventProcessingConfiguration = eventProcessingConfiguration;
    }

    @PostMapping(path = "/eventProcessor/{processorName}/reset")
    public ResponseEntity<String> replayEvents(@PathVariable String processorName) {
        // Get the TrackingEventProcessor
        // -> only the TrackingEventProcessor can be used to perform an event replay
        Optional<TrackingEventProcessor> trackingEventProcessorOptional
                = this.eventProcessingConfiguration.eventProcessor(processorName, TrackingEventProcessor.class);

        // if we find the specified tracking event processor, we have to perform the following actions in the
        // following order to successfully execute an event replay:
        // 1. Shutdown the TrackingEventProcessor
        // 2. Reset the Tokens of the TrackingEventProcessor
        // 3. Restart the TrackingEventProcessor
        if(trackingEventProcessorOptional.isPresent()) {
            TrackingEventProcessor trackingEventProcessor = trackingEventProcessorOptional.get();
            trackingEventProcessor.shutDown();
            trackingEventProcessor.resetTokens();
            trackingEventProcessor.start();

            // if successful, return HTTP:200 Response
            return ResponseEntity.ok().body(
                    String.format("The event processor with the name [%s] has been reset", processorName)
            );
        } else {
            // if not successful, return HTTP:400 Response
            return ResponseEntity.badRequest().body(
                    String.format("The event processor with a name [%s] is not a tracking event processor. " +
                            "Only Tracking event processor is supported.", processorName)
            );
        }

    }

}
