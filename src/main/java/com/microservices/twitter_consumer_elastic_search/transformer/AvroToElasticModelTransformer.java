package com.microservices.twitter_consumer_elastic_search.transformer;

import com.microservices.twitter_consumer_elastic_search.elastic.model.impl.TwitterIndexModel;
import com.microservices.twitter_consumer_elastic_search.kafka.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class AvroToElasticModelTransformer {
    public TwitterIndexModel getElasticModels(TwitterAvroModel twitterAvroModel) {
        return TwitterIndexModel.builder()
                        .userId(twitterAvroModel.getUserId())
                        .id(String.valueOf(twitterAvroModel.getId()))
                        .text(twitterAvroModel.getText())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(twitterAvroModel.getCreatedAt()),
                                ZoneId.systemDefault()))
                        .build();
    }
}
