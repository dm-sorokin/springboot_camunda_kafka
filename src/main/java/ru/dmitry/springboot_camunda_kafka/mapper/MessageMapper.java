package ru.dmitry.springboot_camunda_kafka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import ru.dmitry.springboot_camunda_kafka.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "messageDate", source = "payload.messageDate")
    @Mapping(target = "message", source = "payload.message")
    Message toEntity(MessageDto messageDto);
}
