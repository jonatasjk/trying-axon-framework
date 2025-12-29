package com.jonatask.kyc.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonatask.kyc.domain.event.KycApprovedEvent;
import com.jonatask.kyc.domain.event.KycCreatedEvent;
import com.jonatask.kyc.domain.event.KycRejectedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KycEventPublisher {

    private static final String KYC_CREATED_TOPIC = "kyc-created";
    private static final String KYC_APPROVED_TOPIC = "kyc-approved";
    private static final String KYC_REJECTED_TOPIC = "kyc-rejected";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KycEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @EventHandler
    public void on(KycCreatedEvent event) throws JsonProcessingException {
        log.info("Publishing KycCreatedEvent to Kafka: {}", event.kycId());
        kafkaTemplate.send(KYC_CREATED_TOPIC, event.kycId(), objectMapper.writeValueAsString(event));
    }

    @EventHandler
    public void on(KycApprovedEvent event) throws JsonProcessingException {
        log.info("Publishing KycApprovedEvent to Kafka: {}", event.kycId());
        kafkaTemplate.send(KYC_APPROVED_TOPIC, event.kycId(), objectMapper.writeValueAsString(event));
    }

    @EventHandler
    public void on(KycRejectedEvent event) throws JsonProcessingException {
        log.info("Publishing KycRejectedEvent to Kafka: {}", event.kycId());
        kafkaTemplate.send(KYC_REJECTED_TOPIC, event.kycId(), objectMapper.writeValueAsString(event));
    }
}
