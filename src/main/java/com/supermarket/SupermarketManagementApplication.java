package com.supermarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableKafka
@EnableCaching
@EnableAsync
@EnableTransactionManagement
public class SupermarketManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermarketManagementApplication.class, args);
    }
}