package com.microservices.twitter_consumer_elastic_search.consumer.impl;

import com.microservices.twitter_consumer_elastic_search.config.KafkaConfigData;
import com.microservices.twitter_consumer_elastic_search.consumer.KafkaConsumer;
import com.microservices.twitter_consumer_elastic_search.kafka.admin.KafkaAdminClient;
import com.microservices.twitter_consumer_elastic_search.kafka.model.TwitterAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel>{
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaConsumer.class);

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfigData;

    public TwitterKafkaConsumer(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
                                KafkaAdminClient kafkaAdminClient, KafkaConfigData kafkaConfigData) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaAdminClient = kafkaAdminClient;
        this.kafkaConfigData = kafkaConfigData;
    }

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event) {
        kafkaAdminClient.checkTopicsCreated();
        LOGGER.info("The topic with name: {} is ready for operation", kafkaConfigData.getTopicName());
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer("twitterTopicListener")).start();
    }

    @Override
    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<TwitterAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Long> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partition,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOGGER.info("Message:{}, {} no of messages received with keys {}, partitions: {} and offsets: {}"+
                " sending it to Elastic, Thread-id: {}",
                    messages,
                    messages.size(),
                    keys,
                    partition,
                    offsets,
                    Thread.currentThread().getName()
                );
    }
}
