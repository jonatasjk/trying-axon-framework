package com.jonatask.kyc.infrastructure.kafka;

import com.jonatask.kyc.domain.event.KycApprovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KycEventConsumer {

    @KafkaListener(topics = "kyc-approved", groupId = "kyc-group")
    public void listenKycApproved(
        @Payload KycApprovedEvent event,
        @Header(KafkaHeaders.RECEIVED_KEY) String key
        ) {
        log.info("Consumed KycApprovedEvent with key: {} and event: {}", key, event);
        // Process event for analytics, notifications, etc.
    }
}
