package com.jonatask.kyc.command.aggregate;

import com.jonatask.kyc.command.CreateKycCommand;
import com.jonatask.kyc.command.aggregate.enums.EKycStatus;
import com.jonatask.kyc.domain.event.KycCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class KycAggregate {

    @AggregateIdentifier
    private String kycId;

    private EKycStatus status;

    protected KycAggregate() {}

    @CommandHandler
    public KycAggregate(CreateKycCommand cmd) {
        AggregateLifecycle.apply(
            new KycCreatedEvent(
                cmd.kycId(),
                cmd.customerId(),
                cmd.documentType(),
                cmd.documentNumber()
            )
        );
    }

    @EventSourcingHandler
    public void on(KycCreatedEvent event) {
        this.kycId = event.kycId();
        this.status = EKycStatus.PENDING;
    }
}

