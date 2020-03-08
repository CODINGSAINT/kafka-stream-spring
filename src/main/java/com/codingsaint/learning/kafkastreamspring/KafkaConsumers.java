package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class KafkaConsumers {
    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaConsumers.class);

    @KafkaListener(id="allTopics", topics = {"business","education","faith",
                            "famous-quotes","friendship","future","happiness","inspirational","life",
            "love","nature","politics","proverb","religion","science","success","technology" },
    containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(@Payload Quote quote,
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String incomingTopic,
                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
    ){
        LOGGER.info("Incoming quote {}-> {}",incomingTopic,quote);
    }
}
