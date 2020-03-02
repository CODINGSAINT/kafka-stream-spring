package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class QuoteDeserializer implements Deserializer<Quote> {

    @Override
    public Quote deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Quote quote = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
            quote= (Quote) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quote;
    }

}
