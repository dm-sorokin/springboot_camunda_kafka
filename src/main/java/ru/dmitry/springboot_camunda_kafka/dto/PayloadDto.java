package ru.dmitry.springboot_camunda_kafka.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import ru.dmitry.springboot_camunda_kafka.util.RandomStringBuilder;

@Data
public class PayloadDto {

    private Timestamp messageDate;

    private String message;

    public PayloadDto() {
        this.message = RandomStringBuilder.generateRandomString();
        this.messageDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public Timestamp getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
