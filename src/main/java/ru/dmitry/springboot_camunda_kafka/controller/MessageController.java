package ru.dmitry.springboot_camunda_kafka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    @PostMapping("/send/message")
    public void test() {
        kafkaTemplate.send("start-process-topic", new MessageDto());
    }
}
