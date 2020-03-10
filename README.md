Kafka is most sought after event system today. In this series we will look at Kafka event messaging and streaming.    

# Kafka Stream with Custom Objects 

 
Github Repository link : https://github.com/CODINGSAINT/kafka-stream-spring
    
## What will we be creating

 We will create a streaming application using Spring and kafka streams. 
 We will have a continuously coming stream of famous quotes which will be continously produced at `quote` topic.  Every quote can be tagged with multiple categories i.e. `business,education,faith,famous-quotes,friendship,future,happiness,inspirational,life,love,nature,politics,proverb,religion,science,success,technology` .
 We will have topics related to each of the category. Once a quote comes we will be streaming them to respective category. To keep things simple for demo we have one listener listening to all of the topics
 We have taken quotes from [https://github.com/lukePeavey/quotable/blob/master/data/sample/quotes.json](https://github.com/lukePeavey/quotable/blob/master/data/sample/quotes.json)

These quotes will be streamed and will be sent to respective topic based on their category.
Listener will keep track of all the quotes which are streamed to these topics.

.    
    
## Installing kafka
 ### Download 
 To start kafka we require zookeeper , on kafka website we have different versions you can get the latest stable vesion from [Kafka download page](https://kafka.apache.org/downloads) Say you have downloaded  [**kafka_2.11-2.4.0.tgz**](http://mirrors.estointernet.in/apache/kafka/2.4.0/kafka_2.11-2.4.0.tgz)    
    
`` wget http://mirrors.estointernet.in/apache/kafka/2.4.0/kafka_2.11-2.4.0.tgz `` 
### Unzip 
Use below command to unzip the downloaded file `` tar xzf kafka_2.11-2.4.0.tgz ``
 ### Move to user directory Move content to user directory    
`` mv kafka_2.11-2.4.0/* /usr/local/kafka ``
 ### Run Zookeeper
  Go to kafka directory and run    
`` cd /usr/local/kafka bin/zookeeper-server-start.sh config/zookeeper.properties `` ### Run Kafka Server Use below command to run kafka server    
`` bin/kafka-server-start.sh config/server.properties `` You will see logs confirming the kafka is up an running.    
### Test kafka installation
 #### Create a topic
  ``    
 bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafka-test-topic    
 ``  
#### Verify if topic got created    
    
 bin/kafka-topics.sh --list --zookeeper localhost:2181 kafka-test-topic
 #### Send messages to topic    
Use below command to activate message terminal to kafka-test-topic. Below command will activate message sending to a topic , key in some interesting messages. Lets call it producer window    
    
 

    bin/kafka-console-producer.sh --broker-list localhost:9092 --topic kafka-test-topic >Welcome to Kafka 

   
#### Consume the topic Open another terminal to consume messages    
    
     bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-test-topic --from-beginning    

Now whatever you key in to producer window will be consumed to consumer window    
### Other ways to install kafka
 There could be many other ways to install and run like using apt installer on Ubuntu , using a docker image etc. The above one is a generic setup .    
### Create Topics for for app  

    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic quotes  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic business  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic education  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic faith  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic famous-quotes  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic famous-quotes  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic friendship  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic future  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic happiness  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic inspirational  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic life  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic love  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic nature  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic politics  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic proverb  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic religion  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic science  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic success  
    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic technology  
       


  
  
## Creating Spring Boot Kafka Stream project 
We will create project using https://start.spring.io . 
Go to website and dependencies
 
 ![Spring Boot Kafka Stream dependencies](https://lh3.googleusercontent.com/_X8RJU5IQE1LUUONCukFpCfnANN6HmH3gsVnzirWd74nmWCNluCtMx66uekRE9I1cdYU28jgDjXdbtBNLCOfUMHdxjlZLoIa6gHn5d2pMDjqRJPN9ep4TK9bHdoFaO53M-X5VffpbYz6u1GXh2sLiAmkzAvXvIfs7-to1buqdwascl3WNQzkZ023ygtUVM6avXqr_hXJM9REiOteI-RYIFN-e4XVT9ghfqJBfrEGv7KtARU7NH_EVt2-Lrb8JzeLRGn_2wyaySSC-Anbv11PhW34obCiUE_ws90pCq48v9N6N0upNnYU_PEtW193dXJX3cZLVjmfMrpr9yc8YT_0x6HqNK6qJfoylhoc91hwgJqeecoDCHNRLI2C2mAVOZpunkAJQO_JcibsCLkUlMoahWF41TrJE3j6DPsd-yOHesel-dK_Ngc2Xt5IZu8coWWdZM_BGfWTxMm5e8MIZrDq74AJOEvzFhDeByFH4cOOdqkSooi1-Jqgh_fIumaags3NMNYjtTKAEeRjFVr_Gaa6l37qr8Iaepm8rIRfVlBz3A-HXqOa5KCJ5JAtf8cqc8yOgQlkYC6arT4GsmUsSjOjlxp5e86k65B1z1kDK79Ct4jQBj5DdPNiuL5HnxLZ0EIkGSwqK5EEPEob8ObLeH0noyiBd_5reWwqFMqCPdTX_MVu2DJLXsoha8bpkLqTOxoYsWLmTA3gy80kFV6CcB-9S-uWuabrvL1HK-wIJSUDzipkrgTh=w1190-h669-no)    
    
Download the project and open in your favourite IDE and open it.
Now we have Kafka installed up and running , as a First step we should be creating Quote bean which will flow the quotes from producer to consumer.

    @Getter
    @Setter
    @ToString
    public class Quote {
    private String content;
    private Set<String> tags;
    private String author;
    }



Let's add required configurations for  Kafka. This includes Serializer and Deserializer
for Quotes.We have configurations for Spring Kafka template , producer and consumer.
Serialization and Deserialiation for key and value objects(POJO) have been mentioned seperately.



    spring:
      kafka:
        listener:
          missing-topics-fatal: false
        client-id : quotes-app
        bootstrap-server:
          - localhost:9091
          - localhost:9001
          - localhost:9092
        template:
          default-topic: quotes
        producer:
          key-serializer: org.apache.kafka.common.serialization.StringSerializer
          value-serializer: com.codingsaint.learning.kafkastreamspring.QuoteSerializer
        consumer:
          properties:
            partition:
              assignment:
                strategy: org.apache.kafka.clients.consumer.RoundRobinAssignor
          group-id: random-consumer
          auto-offset-reset: earliest
          key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
          value-deserializer: com.codingsaint.learning.kafkastreamspring.QuoteDeserializer
    ---
    kafka:
      topic:
        input: quotes
        output: business,education,faith,famous-quotes,friendship,future,happiness,inspirational,life,love,nature,politics,proverb,religion,science,success,technology

We will create  QuoteSerializer , QuoteDeserializer and QuoteSerde which will have both serializer and deserializer 
.We are using simple ObjectMapper to serialize and deserialize

##### QuoteSerializer 

    public class QuoteSerializer implements Serializer<Quote> {
    
        @Override
        public byte[] serialize(String s, Quote quote) {
            byte[] retVal = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                retVal = objectMapper.writeValueAsString(quote).getBytes();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retVal;
        }
    }

#### QuoteDeserializer

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

#### QuoteSerde

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

Now Let us see the AppConfig class for configuring Producer and Consumer configurations.

    @Configuration
    @EnableKafka
    @EnableKafkaStreams
    public class AppConfig {
        private static final Logger LOGGER= LoggerFactory.getLogger(AppConfig.class);
    
        @Value("${kafka.topic.input}")
        private String inputTopic;
    
        @Value("#{'${kafka.topic.output}'.split(',')}")
        private List<String> allTopics;
    
    
        @Autowired
        private KafkaProperties kafkaProperties;
    
        /**
         * Configurations for KafkaStreams
         * @param kafkaProperties Will take defaults from application YAML or Properties file with spring.kafka
         * @return kafkaConfiguration
         */
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
    
        /**
         * The Stream which delegates each incoming topic to respective destination topic
         * @param kStreamsBuilder
         * @return
         */
        @Bean
        public KStream<String,Quote> kStream(StreamsBuilder kStreamsBuilder){
            KStream<String,Quote> stream=kStreamsBuilder.stream(inputTopic);
            for(String topic:allTopics){
                stream.filter((s, quote) -> quote.getTags().contains(topic)).to(topic);
            }
            return stream;
    
        }
    
        /**
         * Kafka ConsumerFactory configurations
         * @return
         */
        @Bean
        public ConsumerFactory<String, Quote> consumerFactory() {
            Map<String, Object> props = new HashMap<>();
            props.put(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    kafkaProperties.getBootstrapServers());
            props.put(
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class);
            props.put(
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                    BytesDeserializer.class);
            return new DefaultKafkaConsumerFactory<>(props);
        }
    
        /**
         * Required Configuration for POJO to JSON
         * @return ConcurrentKafkaListenerContainerFactory
         */
        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, Quote>
        kafkaListenerContainerFactory() {
    
            ConcurrentKafkaListenerContainerFactory<String, Quote> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            factory.setMessageConverter(new StringJsonMessageConverter());
            return factory;
        }
    }

-   Since we have injected default KafkaProperties will have required bindings from application.yml .
-   Beans for default Kafka Stream ,consumer factory and `ConcurrentKafkaListenerContainerFactory` is created where `ConcurrentKafkaListenerContainerFactory` bean help us to convert incoming message from String to Json using `StringJsonMessageConverter` 
-   KStream bean helps to create a stream on input topic (quotes) and filter based on tags. For each tag we already have created topics.

Now Let us look at Listener configuration which will listen all the topics

    @Component
    public class TopicConsumers {
        private static final Logger LOGGER = LoggerFactory.getLogger(TopicConsumers.class);
    
        @Value("#{'${kafka.topic.output}'.split(',')}")
        private List<String> allTopics;
    
        /**
         * For simplicity we are listening all topics at one listener
         */
    
        @KafkaListener(id = "allTopics", topics = "#{'${kafka.topic.output}'.split(',')}",
                containerFactory = "kafkaListenerContainerFactory")
        public void consume(@Payload Quote quote,
                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String incomingTopic,
                            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
        ) {
            LOGGER.info("Incoming quote {}-> {}", incomingTopic, quote);
        }
    }

-   For simplicity we are listening all of these topics at one Listener

Once we run the application we will see logs confirming publishing of quotes with different tags and streams sending them to their respective category. Listener will listen incoming quotes.

