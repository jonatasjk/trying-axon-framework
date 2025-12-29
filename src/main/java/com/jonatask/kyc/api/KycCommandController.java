package com.jonatask.kyc.api;

import com.jonatask.kyc.command.CreateKycCommand;
import com.jonatask.kyc.domain.enums.EDocumentType;
import com.jonatask.kyc.dto.CreateKycRequest;
import com.jonatask.kyc.dto.CreateKycResponse;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/kyc")
public class KycCommandController {

    private final CommandGateway commandGateway;

    public KycCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<CreateKycResponse> create(@Valid @RequestBody CreateKycRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        String kycId = UUID.randomUUID().toString();
        return commandGateway.send(
            new CreateKycCommand(
                kycId,
                request.customerId(),
                request.documentType(),
                request.documentNumber()
            )
        ).thenApply(_ -> new CreateKycResponse(kycId));
    }
}
