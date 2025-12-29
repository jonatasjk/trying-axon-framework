package com.jonatask.kyc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "externalKyc", url = "https://mock-kyc")
public interface ExternalKycClient {

    @GetMapping("/verify")
    boolean verify(@RequestParam String documentNumber);
}
