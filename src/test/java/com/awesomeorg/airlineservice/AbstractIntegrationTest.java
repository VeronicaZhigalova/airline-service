package com.awesomeorg.airlineservice;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test-containers")
public abstract class AbstractIntegrationTest {
    @Autowired
    private Flyway flyway;

    public final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach(){
        flyway.migrate();
    }

//    @AfterEach
//    public void afterEach(){
//        flyway.clean();
//    }
}
