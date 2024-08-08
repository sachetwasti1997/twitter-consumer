package com.microservices.twitter_consumer_elastic_search.consumer.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.microservices.twitter_consumer_elastic_search.config.KafkaConfigData;
import com.microservices.twitter_consumer_elastic_search.config.KafkaConsumerConfigData;
import com.microservices.twitter_consumer_elastic_search.consumer.KafkaConsumer;
import com.microservices.twitter_consumer_elastic_search.elastic.index.service.ElasticIndexClient;
import com.microservices.twitter_consumer_elastic_search.elastic.model.impl.TwitterIndexModel;
import com.microservices.twitter_consumer_elastic_search.kafka.admin.KafkaAdminClient;
import com.microservices.twitter_consumer_elastic_search.kafka.model.TwitterAvroModel;
import com.microservices.twitter_consumer_elastic_search.transformer.AvroToElasticModelTransformer;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class TwitterKafkaConsumer implements KafkaConsumer<Long, String>{
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaConsumer.class);

    private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;
    private final AvroToElasticModelTransformer avroToElasticModelTransformer;

    public TwitterKafkaConsumer(ElasticIndexClient<TwitterIndexModel> elasticIndexClient, AvroToElasticModelTransformer avroToElasticModelTransformer, KafkaConsumerConfigData kafkaConsumerConfigData) {
        this.elasticIndexClient = elasticIndexClient;
        this.avroToElasticModelTransformer = avroToElasticModelTransformer;
    }

//    @EventListener
//    public void onAppStarted(ApplicationStartedEvent event) {
//        kafkaAdminClient.checkTopicsCreated();
//        LOGGER.info("The topic with name: {} is ready for operation", kafkaConfigData.getTopicName());
//        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer("twitterTopicListener")).start();
//    }

    @Override
    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload String messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Long> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partition,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("message received keys {}, partitions: {} and offsets: {}"+
                " sending it to Elastic, Thread-id: {}",
                    keys,
                    partition,
                    offsets,
                    Thread.currentThread().getName()
                );

        try {
            TwitterAvroModel twitterAvroModel = objectMapper.readValue(messages, TwitterAvroModel.class);
//            LOGGER.info("Message Received: {}", twitterAvroModel);
        String documentIds = elasticIndexClient.save(avroToElasticModelTransformer.getElasticModels(twitterAvroModel));
        LOGGER.info("Documents saved to elasticsearch with ids: {}", documentIds);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        messages.forEach(twitterAvroModel -> {
//            LOGGER.info("Type of the object received: {}", twitterAvroModel.getClass());
//        });
    }
}
