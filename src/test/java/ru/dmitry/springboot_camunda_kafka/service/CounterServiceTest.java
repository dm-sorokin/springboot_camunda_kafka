package ru.dmitry.springboot_camunda_kafka.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dmitry.springboot_camunda_kafka.entity.Counter;
import ru.dmitry.springboot_camunda_kafka.repository.CounterRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CounterServiceTest {
    @Mock
    private CounterRepository counterRepository;
    @InjectMocks
    private CounterService counterService;

    private Counter counter;

    @BeforeEach
    public void setUp() {
        counter = Counter.builder()
                .value(7)
                .build();
    }

    @Test
    public void testIncrementCounter_gotNullCounter_thenThrowException() {
        Mockito.when(counterRepository.findById(Mockito.anyInt())).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () -> counterService.incrementCounter());
    }

    @Test
    public void testGetValue_gotNullCounter_thenThrowException() {
        Mockito.when(counterRepository.findById(Mockito.anyInt())).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () -> counterService.getValue());
    }

    @Test
    public void testIncrementCounter_gotValidCounter_thenValueIsIncremented() {
        Mockito.when(counterRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(counter));

        long expectedValue = counter.getValue() + 1;
        counterService.incrementCounter();
        long actualValue = counter.getValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testGetValue_gotValidCounter_thenValueIsRecevied() {
        Mockito.when(counterRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(counter));

        int expectedValue = counter.getValue();
        int actualValue = counterService.getValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }
}