package com.jonatask.kyc.dto;

import com.jonatask.kyc.domain.enums.EDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateKycRequest(
    @NotBlank(message = "Customer ID is required")
    String customerId,

    @NotNull(message = "Document type is required")
    EDocumentType documentType,

    @NotBlank(message = "Document number is required")
    String documentNumber
) {}
