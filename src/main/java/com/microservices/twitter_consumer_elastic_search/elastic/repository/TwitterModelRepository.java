package com.microservices.twitter_consumer_elastic_search.elastic.repository;

import com.microservices.twitter_consumer_elastic_search.elastic.model.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterModelRepository extends ElasticsearchRepository<TwitterIndexModel, String> {
}
