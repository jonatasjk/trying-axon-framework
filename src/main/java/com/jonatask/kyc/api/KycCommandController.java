package com.jonatask.kyc.api;

import com.jonatask.kyc.command.CreateKycCommand;
import com.jonatask.kyc.dto.CreateKycRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/kyc")
public class KycCommandController {

    private final CommandGateway commandGateway;

    public KycCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void create(@RequestBody CreateKycRequest req) {
        commandGateway.send(
            new CreateKycCommand(
                UUID.randomUUID().toString(),
                req.customerId(),
                req.documentNumber()
            )
        );
    }
}

