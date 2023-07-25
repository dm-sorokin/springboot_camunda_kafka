package ru.dmitry.springboot_camunda_kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationDto {
    @JsonProperty(required = true)
    private int key;
    @JsonProperty(required = true)
    private int counter;

    public NotificationDto(int key, int counter) {
        this.key = key;
        this.counter = counter;
    }


}
