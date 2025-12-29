package com.jonatask.kyc.event;

public record KycCreatedEvent(
    String kycId,
    String customerId,
    String documentNumber
) {}
