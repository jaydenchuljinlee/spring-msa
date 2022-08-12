package com.bbrick.trade.config.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaProducer {
    private static final String TOPIC = "order";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrder(Object message) {
        this.kafkaTemplate.send(TOPIC, message);
    }

}
