package com.jonatask.kyc.saga;

import com.jonatask.kyc.client.ExternalKycClient;
import com.jonatask.kyc.event.KycCreatedEvent;
import com.jonatask.kyc.event.KycApprovedEvent;
import com.jonatask.kyc.event.KycRejectedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class KycSaga {

    @Autowired
    private transient ExternalKycClient kycClient;

    @Autowired
    private transient CommandGateway commandGateway;

    @SagaEventHandler(associationProperty = "kycId")
    @StartSaga
    public void on(KycCreatedEvent event) {

        boolean approved = kycClient.verify(
            event.documentNumber()
        );

        if (approved) {
            commandGateway.send(
                new KycApprovedEvent(event.kycId())
            );
        } else {
            commandGateway.send(
                new KycRejectedEvent(event.kycId())
            );
        }
    }

    @SagaEventHandler(associationProperty = "kycId")
    @EndSaga
    public void on(KycApprovedEvent event) {}

    @SagaEventHandler(associationProperty = "kycId")
    @EndSaga
    public void on(KycRejectedEvent event) {}
}

