package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumers {



    @KafkaListener(topics = {"love"})
    public void consume(Quote quote){

        System.out.println("Incoming Love "+quote);

    }
}
