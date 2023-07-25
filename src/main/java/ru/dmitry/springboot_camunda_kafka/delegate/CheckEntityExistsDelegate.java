package ru.dmitry.springboot_camunda_kafka.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import ru.dmitry.springboot_camunda_kafka.service.CounterService;
import ru.dmitry.springboot_camunda_kafka.service.MessageService;

@Component
@RequiredArgsConstructor
public class CheckEntityExistsDelegate implements JavaDelegate {

    private final static Logger logger = LoggerFactory.getLogger(CheckEntityExistsDelegate.class);
    private final MessageService messageService;
    private final CounterService counterService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Checking key existing in database ... ");
        MessageDto messageDto = (MessageDto) execution.getVariable("dataMessage");
        if (messageService.isMessageExists(messageDto)) {
            counterService.incrementCounter();
            execution.setVariable("counter", counterService.getValue());
            logger.info("Such key already exists - counter is incremented");
        }
        else {
            logger.info("Such key does not exists");
        }
        logger.info("counter value: " + counterService.getValue());
    }
}
