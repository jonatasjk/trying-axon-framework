package com.jonatask.kyc.repository;

import com.jonatask.kyc.projection.KycView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycViewRepository extends MongoRepository<KycView, String> {
}
