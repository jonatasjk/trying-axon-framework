package com.jonatask.kyc.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateKycCommand {
    @TargetAggregateIdentifier
    String kycId;
    String customerId;
    String documentNumber;
}
