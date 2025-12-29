package com.jonatask.kyc.domain.event;

import com.jonatask.kyc.command.aggregate.enums.EDocumentType;

public record KycCreatedEvent(
    String kycId,
    String customerId,
    EDocumentType documentType,
    String documentNumber
) {}
