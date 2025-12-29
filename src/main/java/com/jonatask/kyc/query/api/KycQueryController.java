package com.jonatask.kyc.query.api;

import com.jonatask.kyc.query.projection.KycView;
import com.jonatask.kyc.query.repository.KycViewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/kyc")
public class KycQueryController {

    private final KycViewRepository repo;

    public KycQueryController(KycViewRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{id}")
    public KycView get(@PathVariable String id) {
        return repo.findById(id).orElseThrow();
    }
}

