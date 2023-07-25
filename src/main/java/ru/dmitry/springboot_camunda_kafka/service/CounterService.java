package ru.dmitry.springboot_camunda_kafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmitry.springboot_camunda_kafka.entity.Counter;
import ru.dmitry.springboot_camunda_kafka.repository.CounterRepository;

@Service
@RequiredArgsConstructor
public class CounterService {
    private final CounterRepository counterRepository;

    public void incrementCounter() {
        Counter counter = counterRepository.findById(1).orElseThrow(RuntimeException::new);
        int newCounter = counter.getValue() + 1;
        counter.setValue(newCounter);
    }

    public int getValue() {
        Counter counter = counterRepository.findById(1).orElseThrow(RuntimeException::new);
        return counter.getValue();
    }
}
