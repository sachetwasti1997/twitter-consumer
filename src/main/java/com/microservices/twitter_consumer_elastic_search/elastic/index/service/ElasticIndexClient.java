package com.microservices.twitter_consumer_elastic_search.elastic.index.service;

import com.microservices.twitter_consumer_elastic_search.elastic.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
