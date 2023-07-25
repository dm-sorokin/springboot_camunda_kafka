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
public class SuccessRequestsDelegate implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(SuccessRequestsDelegate.class);
    private final KafkaTemplate<String, StatusMessageDto> kafkaTemplate;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("handling success responses...");
        MessageDto messageDto = (MessageDto) execution.getVariable("dataMessage");
        StatusMessageDto statusMessageDto = new StatusMessageDto(messageDto.getKey(), 0);
        kafkaTemplate.send("success-requests-topic", statusMessageDto);
        logger.info("Message sent to topic \"success-requests-topic\"");
    }
}
