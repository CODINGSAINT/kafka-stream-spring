package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
public class QuotesProduces {

    private static final String RANDOM_QUOTE="https://api.quotable.io/random";

    private static final Logger LOGGER= LoggerFactory.getLogger(QuotesProduces.class);
    private WebClient webClient;


    @Autowired
    private KafkaTemplate<String, Quote> kafkaTemplate;

    public QuotesProduces(WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder.build();
    }

    @Scheduled(fixedRate = 10000)
    public void quotes() {

        Mono<Quote>  quote=webClient.get().uri(RANDOM_QUOTE).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Quote.class);
        LOGGER.info("quotes-> {}",quote.block());
        kafkaTemplate.sendDefault(quote.block());
    }

}
