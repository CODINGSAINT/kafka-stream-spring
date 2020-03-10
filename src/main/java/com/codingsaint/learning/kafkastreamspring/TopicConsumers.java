package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicConsumers {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicConsumers.class);

    @Value("#{'${kafka.topic.output}'.split(',')}")
    private List<String> allTopics;

    /**
     * For simplicity we are listening all topics at one listener
     */

    @KafkaListener(id = "allTopics", topics = "#{'${kafka.topic.output}'.split(',')}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload Quote quote,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String incomingTopic,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
    ) {
        LOGGER.info("Incoming quote {}-> {}", incomingTopic, quote);
    }
}
