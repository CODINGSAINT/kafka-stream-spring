package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class QuoteDeserializer implements Deserializer<Quote> {

    @Override
    public Quote deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Quote quote = null;
        try {
            quote = mapper.readValue(bytes, Quote.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quote;
    }

}
