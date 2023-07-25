package ru.dmitry.springboot_camunda_kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmitry.springboot_camunda_kafka.entity.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Integer> {
}
