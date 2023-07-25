package ru.dmitry.springboot_camunda_kafka.delegate;


import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import ru.dmitry.springboot_camunda_kafka.entity.Message;
import ru.dmitry.springboot_camunda_kafka.service.MessageService;

@Component
@RequiredArgsConstructor
public class SaveMessageDelegate implements JavaDelegate {

    private final static Logger logger = LoggerFactory.getLogger(SaveMessageDelegate.class);
    private final MessageService messageService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Saving message ... ");
        MessageDto messageDto = (MessageDto) execution.getVariable("dataMessage");
        Message message = messageService.save(messageDto);
        if (message != null) {
            logger.info("Message is saved!");
        }
    }
}
