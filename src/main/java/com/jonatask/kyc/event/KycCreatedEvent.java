package com.jonatask.kyc.event;

import com.jonatask.kyc.domain.enums.EDocumentType;

public record KycCreatedEvent(
    String kycId,
    String customerId,
    EDocumentType documentType,
    String documentNumber
) {}
