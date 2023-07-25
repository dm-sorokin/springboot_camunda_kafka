package ru.dmitry.springboot_camunda_kafka.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import ru.dmitry.springboot_camunda_kafka.dto.StatusMessageDto;

@Component
@RequiredArgsConstructor
public class FailedRequestsDelegate implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(FailedRequestsDelegate.class);
    private final KafkaTemplate<String, StatusMessageDto> kafkaTemplate;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("handling failed responses");
        MessageDto messageDto = (MessageDto) execution.getVariable("dataMessage");
        StatusMessageDto statusMessageDto = new StatusMessageDto(messageDto.getKey(), -1);
        kafkaTemplate.send("failed-requests-topic", statusMessageDto);
        logger.info("Message sent to topic \"failed-requests-topic\"");
    }
}
