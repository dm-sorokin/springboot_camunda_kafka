package ru.dmitry.springboot_camunda_kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import ru.dmitry.springboot_camunda_kafka.entity.Message;
import ru.dmitry.springboot_camunda_kafka.mapper.MessageMapper;
import ru.dmitry.springboot_camunda_kafka.repository.MessageRepository;

@Service
@Slf4j
public class MessageService {
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageMapper messageMapper, MessageRepository messageRepository) {
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
    }

    public Message save(MessageDto messageDto) {
        Message message = messageMapper.toEntity(messageDto);
        return messageRepository.save(message);
    }

    public boolean isMessageExists(MessageDto messageDto) {
        Message newMessage = messageMapper.toEntity(messageDto);
        return messageRepository.existsByKey(newMessage.getKey());
    }
}