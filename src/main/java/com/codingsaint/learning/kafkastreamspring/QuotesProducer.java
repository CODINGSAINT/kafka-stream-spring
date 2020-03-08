package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class QuotesProducer {


    private static final Logger LOGGER= LoggerFactory.getLogger(QuotesProducer.class);

    private List<Quote> quotes= new ArrayList<>();

    @Autowired
    private KafkaTemplate<String, Quote> kafkaTemplate;


    @Scheduled(fixedRate = 10000)
    public Quote quotes() {
        Quote quote=getQuotes();
        LOGGER.info("Producing Quote :{}",quote);
        kafkaTemplate.sendDefault(quote);
        return quote;
    }

    private Quote getQuotes()  {
        if(quotes.size()==0){
            try{
                Resource resource=new ClassPathResource("quotes.json");
                ObjectMapper mapper= new ObjectMapper();
                quotes= mapper.readValue(resource.getInputStream(),  new TypeReference<List<Quote>>() {});

            }catch(IOException e){
                LOGGER.error("Error reading quotes.json {}",e.getMessage());
            }
           }

       return quotes.get(random(quotes.size()));
    }

    private Integer random(Integer size) {
        Random r= new Random();
        return r.nextInt(size);
    }


}
