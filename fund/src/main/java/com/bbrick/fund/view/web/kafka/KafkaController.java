package com.bbrick.fund.view.web.kafka;

import com.bbrick.fund.config.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class KafkaController {
    private final KafkaProducer kafkaProducer;

    @PostMapping("/kafka")
    public String sendMessage(@RequestParam("message") String message) {
        this.kafkaProducer.sendMessage(message);

        return "success";
    }
}
