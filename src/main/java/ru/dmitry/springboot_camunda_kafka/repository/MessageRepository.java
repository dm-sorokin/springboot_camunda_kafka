package ru.dmitry.springboot_camunda_kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmitry.springboot_camunda_kafka.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    boolean existsByKey(Integer id);
}

