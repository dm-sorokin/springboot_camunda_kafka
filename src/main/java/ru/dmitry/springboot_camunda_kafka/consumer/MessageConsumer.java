package ru.dmitry.springboot_camunda_kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import ru.dmitry.springboot_camunda_kafka.dto.StatusMessageDto;
import ru.dmitry.springboot_camunda_kafka.service.CamundaService;


@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {
    private final static String MESSAGE_START = "StartProcess";
    private final static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private final CamundaService camundaService;

    @KafkaListener(topics = "start-process-topic")
    public void startProcess(MessageDto messageDto) {
        logger.info("============================START======================================");
        logger.info("Received message" + messageDto.toString());
        camundaService.correlate(messageDto, MESSAGE_START);
    }

    @KafkaListener(topics = "success-requests-topic")
    public void consumeSuccessMessage(StatusMessageDto statusMessageDto) {
        logger.info("success-requests-topic: received message - " + statusMessageDto.toString());
        logger.info("========================END=======================================");
    }
    @KafkaListener(topics = "failed-requests-topic")
    public void consumeFailedMessage(StatusMessageDto statusMessageDto) {
        logger.info("failed-requests-topic: received message - " + statusMessageDto.toString());
        logger.info("========================END=======================================");
    }
}
