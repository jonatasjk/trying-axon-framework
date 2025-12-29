package com.jonatask.kyc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
	"spring.cloud.openfeign.client.config.default.url=http://localhost:8080",
	"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration"
})
class KycApplicationTests {

	@Test
	void contextLoads() {
	}

}
