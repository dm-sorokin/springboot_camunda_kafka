package ru.dmitry.springboot_camunda_kafka.controller;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dmitry.springboot_camunda_kafka.dto.MessageDto;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = "start-process-topic", bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class MessageControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    private final String TOPIC_NAME = "start-process-topic";

    @Test
    public void test() throws Exception {
        Consumer<String, MessageDto> consumerServiceTest = createConsumer(TOPIC_NAME);

        var requestBuilder = MockMvcRequestBuilders.post("/kafka/send/message");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        ConsumerRecord<String, MessageDto> record =
                KafkaTestUtils.getSingleRecord(consumerServiceTest, TOPIC_NAME);
        MessageDto messageReceived = record.value();
        int key = messageReceived.getKey();
        String message = messageReceived.getPayload().getMessage();

        Assertions.assertTrue(key > 0 && key <= 30,
                "Key value is not in the range");
        Assertions.assertTrue(message.length() > 0 && message.length() <= 10,
                "Message length is not in the range");
    }
    private Consumer<String, MessageDto> createConsumer(final String topicName) {
        Map<String, Object> consumerProps =
                KafkaTestUtils.consumerProps("group_consumer_test", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<String, MessageDto> consumer =
                new DefaultKafkaConsumerFactory<>(consumerProps,
                        new StringDeserializer(),
                        new JsonDeserializer<>(MessageDto.class, false)).createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, topicName);

        return consumer;
    }
}