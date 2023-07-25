package ru.dmitry.springboot_camunda_kafka.dto;

import lombok.Data;

import java.util.Random;

@Data
public class MessageDto {
    private Integer key;
    private PayloadDto payload;

    public MessageDto() {
        this.key = new Random().nextInt(30) + 1;
        this.payload = new PayloadDto();
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public PayloadDto getPayload() {
        return payload;
    }

    public void setPayload(PayloadDto payload) {
        this.payload = payload;
    }
}
