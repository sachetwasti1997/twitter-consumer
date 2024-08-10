package com.microservices.twitter_consumer_elastic_search.consumer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.twitter_consumer_elastic_search.consumer.KafkaConsumer;
import com.microservices.twitter_consumer_elastic_search.kafka.model.TwitterAvroModel;
import com.microservices.twitter_consumer_elastic_search.service.impl.TwitterElasticRepositoryIndexClient;
import com.microservices.twitter_consumer_elastic_search.transformer.TwitterAvroModelToPosts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterKafkaConsumer implements KafkaConsumer<Long, String>{
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaConsumer.class);

    private final TwitterElasticRepositoryIndexClient twitterElasticRepositoryIndexClient;
    private final TwitterAvroModelToPosts twitterAvroModelToPosts;

    public TwitterKafkaConsumer(TwitterElasticRepositoryIndexClient twitterElasticRepositoryIndexClient,
                                TwitterAvroModelToPosts twitterAvroModelToPosts) {
        this.twitterElasticRepositoryIndexClient = twitterElasticRepositoryIndexClient;
        this.twitterAvroModelToPosts = twitterAvroModelToPosts;
    }

    @Override
    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload String messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Long> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partition,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("message received with key {}, partitions: {} and offsets: {}"+
                " sending it to Elastic, Thread-id: {}",
                    keys,
                    partition,
                    offsets,
                    Thread.currentThread().getName()
                );

        try {
            TwitterAvroModel twitterAvroModel = objectMapper.readValue(messages, TwitterAvroModel.class);
            String id = twitterElasticRepositoryIndexClient.save(twitterAvroModelToPosts.convertModel(twitterAvroModel));
            LOGGER.info("Saved the post with id: {}", id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        messages.forEach(twitterAvroModel -> {
//            LOGGER.info("Type of the object received: {}", twitterAvroModel.getClass());
//        });
    }
}
