package com.microservices.twitter_consumer_elastic_search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.microservices.twitter_consumer_elastic_search.elastic.model.IndexModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ElasticIndexClient <T extends IndexModel>{
    String save(T documents);
}
