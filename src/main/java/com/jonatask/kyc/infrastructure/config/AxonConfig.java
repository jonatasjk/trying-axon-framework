package com.jonatask.kyc.infrastructure.config;

import com.mongodb.client.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public org.axonframework.extensions.mongo.MongoTemplate axonMongoTemplate(MongoClient mongoClient) {
        return DefaultMongoTemplate.builder()
            .mongoDatabase(mongoClient, "kyc-db")
            .build();
    }

    @Bean
    public TokenStore tokenStore(org.axonframework.extensions.mongo.MongoTemplate axonMongoTemplate) {
        return MongoTokenStore.builder()
            .mongoTemplate(axonMongoTemplate)
            .serializer(XStreamSerializer.defaultSerializer())
            .build();
    }
}
