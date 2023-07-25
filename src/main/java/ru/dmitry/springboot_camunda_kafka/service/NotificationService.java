package ru.dmitry.springboot_camunda_kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.dmitry.springboot_camunda_kafka.dto.NotificationDto;

@Service
@Slf4j
public class NotificationService {
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    final static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public ResponseEntity<String> postNotification(NotificationDto notificationDto) {
        HttpEntity<NotificationDto> request = new HttpEntity<>(notificationDto, headers);
        String url = "http://localhost:8081/api/v1/notification";

        try {
            logger.info("body request: " + notificationDto);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } catch (HttpClientErrorException e) {
            logger.info("Client error: " + e.getStatusCode());
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        } catch (HttpServerErrorException e) {
            logger.info("Server error: " + e.getStatusCode());
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
}
