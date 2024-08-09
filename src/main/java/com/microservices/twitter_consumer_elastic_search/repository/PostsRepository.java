package com.microservices.twitter_consumer_elastic_search.repository;

import com.microservices.twitter_consumer_elastic_search.kafka.model.Posts;
import com.microservices.twitter_consumer_elastic_search.kafka.model.TwitterAvroModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepository extends MongoRepository<Posts, String> {
}
