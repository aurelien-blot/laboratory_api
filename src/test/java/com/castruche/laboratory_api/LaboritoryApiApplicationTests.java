package com.castruche.laboratory_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.flyway.enabled=false")
@ActiveProfiles("test")
class LaboratoryApiApplicationTests {

    @org.junit.jupiter.api.Test
    void contextLoads() {}

}
