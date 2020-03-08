package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class KafkaConsumers {
    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaConsumers.class);

    @KafkaListener(id="allTopics", topics = {"business","education","faith",
                            "famous-quotes","friendship","future","happiness","inspirational","life",
            "love","nature","politics","proverb","religion","science","success","technology" }, groupId = "random-consumer")
    public void consume( Quote quote){
        LOGGER.info("Incoming quote {}",quote);
    }
}
