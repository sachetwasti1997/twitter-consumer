package com.microservices.twitter_consumer_elastic_search.transformer;

import com.microservices.twitter_consumer_elastic_search.kafka.model.Posts;
import com.microservices.twitter_consumer_elastic_search.kafka.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TwitterAvroModelToPosts {
    public Posts convertToPost(TwitterAvroModel twitterAvroModel) {
        return Posts
                .builder()
                .id(twitterAvroModel.getId())
                .text(twitterAvroModel.getText())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(twitterAvroModel.getCreatedAt()), ZoneId.systemDefault()).toString())
                .build();
    }
}
