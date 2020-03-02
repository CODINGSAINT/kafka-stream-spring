package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import com.codingsaint.learning.kafkastreamspring.model.QuoteSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class AppConfig {
    @Value("${kafka.topic.input}")
    private String inputTopic;

    @Value("${kafka.topic.life}")
    private String lifeTopic;

    @Autowired
    private KafkaProperties kafkaProperties;
    @Bean(name= KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaConfiguration(final KafkaProperties kafkaProperties){
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, QuoteSerde.class.getName() );
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        return new KafkaStreamsConfiguration(config);
    }

    @Bean
    public KStream<String,Quote> kStream(StreamsBuilder kStreamsBuilder){

        KStream<String,Quote> stream=kStreamsBuilder.stream(inputTopic);
        stream.filter((s, quote) -> {
            return quote.getCategories().contains("love");
        }).to("love");

        return stream;

    }

}
