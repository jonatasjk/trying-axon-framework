package com.jonatask.kyc.command;

import com.jonatask.kyc.domain.enums.EDocumentType;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateKycCommand (
    @TargetAggregateIdentifier String kycId,
    String customerId,
    EDocumentType documentType,
    String documentNumber
) {}
