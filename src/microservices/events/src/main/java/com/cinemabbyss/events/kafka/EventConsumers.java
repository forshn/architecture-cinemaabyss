package com.cinemabbyss.events.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumers {

    @KafkaListener(topics = "movie-events", groupId = "events-service")
    public void onMovie(ConsumerRecord<String, String> rec) {
        log.info("movie-events key={} partition={} offset={} payload={}", rec.key(), rec.partition(), rec.offset(), rec.value());
    }

    @KafkaListener(topics = "user-events", groupId = "events-service")
    public void onUser(ConsumerRecord<String, String> rec) {
        log.info("user-events key={} partition={} offset={} payload={}", rec.key(), rec.partition(), rec.offset(), rec.value());
    }

    @KafkaListener(topics = "payment-events", groupId = "events-service")
    public void onPayment(ConsumerRecord<String, String> rec) {
        log.info("payment-events key={} partition={} offset={} payload={}", rec.key(), rec.partition(), rec.offset(), rec.value());
    }
}
