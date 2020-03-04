package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.QuoteDeserializer;
import com.codingsaint.learning.kafkastreamspring.QuoteSerializer;
import com.codingsaint.learning.kafkastreamspring.model.Quote;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class QuoteSerde implements Serde<Quote> {
    public QuoteSerde() {
    }

    @Override
    public Serializer<Quote> serializer() {
        return new QuoteSerializer();
    }

    @Override
    public Deserializer<Quote> deserializer() {
        return new QuoteDeserializer();
    }
}
