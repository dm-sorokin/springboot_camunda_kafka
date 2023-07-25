package ru.dmitry.springboot_camunda_kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.metrics.stats.Count;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dmitry.springboot_camunda_kafka.consumer.MessageConsumer;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class CamundaService {
    private final RuntimeService runtimeService;
    private final CounterService counterService;
    private final static Logger logger = LoggerFactory.getLogger(CamundaService.class);
    public void correlate(MessageDto messageDto, String message) {
        logger.info("Consuming message: " + message);

        MessageCorrelationBuilder builder = runtimeService.createMessageCorrelation(message);
        builder.setVariable("dataMessage", messageDto);
        builder.setVariable("counter", counterService.getValue());
        logger.info("Executing camunda task... ");
        builder.correlate();

    }
}
