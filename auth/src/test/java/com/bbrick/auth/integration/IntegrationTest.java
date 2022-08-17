package com.bbrick.auth.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql");

    @DynamicPropertySource
    public static void registerMysSQLProperties(DynamicPropertyRegistry propertyRegistry) {
        if (mySQLContainer.isRunning()) {
            return;
        }

        startMySQLContainer(mySQLContainer);
        setMySQLProperties(mySQLContainer, propertyRegistry);
    }

    private static void startMySQLContainer(MySQLContainer mySQLContainer) {
        log.info("MySQL Test Container is Getting started");

        mySQLContainer
                .withUsername("root")
                .withPassword("1234")
                .withDatabaseName("bbrick")
                .start();

        log.info("MySQL Test Conainer is now running");
    }

    private static void setMySQLProperties(MySQLContainer mySQLContainer, DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.write.hikari.jdbc-url", mySQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.write.hikari.username", mySQLContainer::getUsername);
        propertyRegistry.add("spring.datasource.write.hikari.password", mySQLContainer::getPassword);

        propertyRegistry.add("spring.datasource.write.read-only.jdbc-url", mySQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.write.read-only.username", mySQLContainer::getUsername);
        propertyRegistry.add("spring.datasource.write.read-only.password", mySQLContainer::getPassword);
    }

    @Autowired
    private List<JpaRepository> repositories;

    @BeforeEach
    void beforeEach() { this.clearData(); }

    private void clearData() { this.repositories.forEach(JpaRepository::deleteAllInBatch); }
}
