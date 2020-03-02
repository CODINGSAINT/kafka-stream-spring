
Kafka is most sought after event system today. In this series we will look at Kafka event messaging and streaming.

## What will we be creating
We will create a streaming application using Spring and kafka streams. We will have topics with following data
qoutes-topic
categories-topic

We will simulate live events by scheduling task to call to a API for quotes (https://quotes.rest ) . This api will give us quotes with categories topic. As soon as a topic comes with a certain categories it will be delivered to that topic. 
A subscriber to that topic will be fed with the quotes of his interest.

## Installing kafka
### Download 
To start kafka we require zookeeper , on kafka website we have different versions you can get the latest stable vesion from [Kafka download page](https://kafka.apache.org/downloads)  
Say you have downloaded  [**kafka_2.11-2.4.0.tgz**](http://mirrors.estointernet.in/apache/kafka/2.4.0/kafka_2.11-2.4.0.tgz)

``
wget http://mirrors.estointernet.in/apache/kafka/2.4.0/kafka_2.11-2.4.0.tgz 
``
### Unzip 
Use below command to unzip the downloaded file
``
tar xzf kafka_2.11-2.4.0.tgz
``
### Move to user directory
Move content to user directory
``
mv kafka_2.11-2.4.0/* /usr/local/kafka
``
### Run Zookeeper
Go to kafka directory and run
``
cd /usr/local/kafka
bin/zookeeper-server-start.sh config/zookeeper.properties
``
### Run Kafka Server
Use below command to run kafka server
``
bin/kafka-server-start.sh config/server.properties
``
You will see logs confirming the kafka is up an running.
### Test kafka installation
#### Create a topic

    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafka-test-topic

#### Verify if topic got created

    bin/kafka-topics.sh --list --zookeeper localhost:2181
    kafka-test-topic
#### Send messages to topic
Use below command to activate message terminal to kafka-test-topic. Below command will activate message sending to a topic , key in some interesting messages. Lets call it producer window

    bin/kafka-console-producer.sh --broker-list localhost:9092 --topic kafka-test-topic
    >Welcome to Kafka

#### Consume the topic 
Open another terminal to consume messages

    bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka-test-topic --from-beginning

Now whatever you key in to producer window will be consumed to consumer window
### Other ways to install kafka
There could be many other ways to install and run like using apt installer on Ubuntu , using a docker image etc. The above one is a generic setup .
## Creating Spring Boot Kafka Stream project
We will create project using https://start.spring.io . Go to website and dependencies ![Spring Boot Kafka Stream dependencies](https://photos.google.com/u/1/album/AF1QipNTpi7Vn95Ont9CiV1MQtHDJ6c4LVHiC8HIFiVn/photo/AF1QipNGWd_8OcAeaXRUAjrvPVU5ecrdaA98_vHBM10v)

Download the project and open in your favourite IDE and open it.

