package com.jonatask.kyc.query.projection;

import com.jonatask.kyc.command.aggregate.enums.EKycStatus;
import com.jonatask.kyc.domain.event.KycApprovedEvent;
import com.jonatask.kyc.domain.event.KycCreatedEvent;
import com.jonatask.kyc.domain.event.KycRejectedEvent;
import com.jonatask.kyc.query.repository.KycViewRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class KycProjection {

    private final KycViewRepository repo;

    public KycProjection(KycViewRepository repo) {
        this.repo = repo;
    }

    @EventHandler
    public void on(KycCreatedEvent event) {
        repo.save(new KycView(
            event.kycId(),
            event.customerId(),
            event.documentType(),
            EKycStatus.PENDING
        ));
    }

    @EventHandler
    public void on(KycApprovedEvent event) {
        repo.findById(event.kycId())
            .ifPresent(v -> {
                v.setStatus(EKycStatus.APPROVED);
                repo.save(v);
            });
    }

    @EventHandler
    public void on(KycRejectedEvent event) {
        repo.findById(event.kycId())
            .ifPresent(v -> {
                v.setStatus(EKycStatus.REJECTED);
                repo.save(v);
            });
    }
}

