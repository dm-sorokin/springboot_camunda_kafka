package ru.dmitry.springboot_camunda_kafka.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.dmitry.springboot_camunda_kafka.dto.NotificationDto;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import ru.dmitry.springboot_camunda_kafka.service.CounterService;
import ru.dmitry.springboot_camunda_kafka.service.NotificationService;

@Component
public class PostNotificationDelegate implements JavaDelegate {
    private final NotificationService notificationService;
    private final CounterService counterService;
    private final static Logger logger = LoggerFactory.getLogger(PostNotificationDelegate.class);

    @Autowired
    public PostNotificationDelegate(NotificationService notificationService, CounterService counterService) {
        this.notificationService = notificationService;
        this.counterService = counterService;
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Posting notification... ");
        MessageDto messageDto = (MessageDto) execution.getVariable("dataMessage");
        int key = messageDto.getKey();
        int counter = counterService.getValue();
        NotificationDto notificationDto = new NotificationDto(key, counter);
        ResponseEntity<String> responseEntity = notificationService.postNotification(notificationDto);
        execution.setVariable("statusCode", responseEntity.getStatusCodeValue());
    }
}
