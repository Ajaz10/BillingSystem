package com.supermarket.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topics.order-placed}")
    private String orderPlacedTopic;

    @Value("${kafka.topics.inventory-update}")
    private String inventoryUpdateTopic;

    @Value("${kafka.topics.daily-summary}")
    private String dailySummaryTopic;

    @Value("${kafka.topics.stock-low}")
    private String stockLowTopic;

    @Value("${kafka.topics.payment-confirmed}")
    private String paymentConfirmedTopic;

    @Bean
    public NewTopic orderPlacedTopic() {
        return TopicBuilder.name(orderPlacedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inventoryUpdateTopic() {
        return TopicBuilder.name(inventoryUpdateTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic dailySummaryTopic() {
        return TopicBuilder.name(dailySummaryTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic stockLowTopic() {
        return TopicBuilder.name(stockLowTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentConfirmedTopic() {
        return TopicBuilder.name(paymentConfirmedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}