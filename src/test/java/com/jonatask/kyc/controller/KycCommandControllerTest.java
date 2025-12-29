package com.jonatask.kyc.controller;

import com.jonatask.kyc.api.KycCommandController;
import com.jonatask.kyc.command.CreateKycCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KycCommandController.class)
class KycCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommandGateway commandGateway;

    @Test
    void shouldCreateKyc() throws Exception {
        // Given
        String kycId = UUID.randomUUID().toString();
        String requestBody = """
                {
                    "customerId": "81e42ea8-9f11-46ec-977b-4aacf50e7ce5",
                    "documentType: "CPF",
                    "documentNumber": "09876543210"
                }
                """;

        when(commandGateway.send(any(CreateKycCommand.class)))
            .thenReturn(CompletableFuture.completedFuture(kycId));

        // When & Then
        var mvcResult = mockMvc.perform(post("/v1/kyc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(request().asyncStarted())
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.kycId").exists());

        verify(commandGateway).send(any(CreateKycCommand.class));
    }
}

