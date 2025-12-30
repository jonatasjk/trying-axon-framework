package com.jonatask.kyc.query.projection;

import com.jonatask.kyc.command.aggregate.enums.EKycStatus;
import com.jonatask.kyc.domain.event.KycApprovedEvent;
import com.jonatask.kyc.domain.event.KycCreatedEvent;
import com.jonatask.kyc.domain.event.KycRejectedEvent;
import com.jonatask.kyc.query.repository.KycViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("com.jonatask.kyc.query.projection")
@Slf4j
public class KycProjection {

    private final KycViewRepository repository;

    public KycProjection(KycViewRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(KycCreatedEvent event) {
        try {
            KycView document = new KycView(
                event.kycId(),
                event.customerId(),
                event.documentType(),
                EKycStatus.PENDING
            );

            repository.save(document);
        } catch (Exception ex) {
            log.error("Failed to process KycCreatedEvent for kycId {}: {}",
                event.kycId(), ex.getMessage());
        }
    }

    @EventHandler
    public void on(KycApprovedEvent event) {
        repository.findById(event.kycId())
            .ifPresent(v -> {
                v.setStatus(EKycStatus.APPROVED);
                repository.save(v);
            });
    }

    @EventHandler
    public void on(KycRejectedEvent event) {
        repository.findById(event.kycId())
            .ifPresent(v -> {
                v.setStatus(EKycStatus.REJECTED);
                repository.save(v);
            });
    }
}

