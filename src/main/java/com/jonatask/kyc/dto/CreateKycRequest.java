package com.jonatask.kyc.dto;

public record CreateKycRequest(
    String customerId,
    String documentNumber
) {}
