package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
public class QuotesProduces {

    private WebClient webClient;


    @Autowired
    private KafkaTemplate<String, Quote> kafkaTemplate;

    public QuotesProduces(WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder.build();
    }

    @Scheduled(fixedRate = 10000)
    public void quotes() {
        Quote quote = new Quote();
        quote.setQuote("Test");
        Set<String> q= new HashSet<>();
        q.add("love");
        quote.setCategories(q);
        quote.setQuote("Test");
        System.out.println(quote);
        ObjectMapper mapper= new ObjectMapper();
        kafkaTemplate.sendDefault(quote);
    }

}
