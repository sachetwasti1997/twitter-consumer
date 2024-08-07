package com.microservices.twitter_consumer_elastic_search.elastic.index.service.impl;

import com.microservices.twitter_consumer_elastic_search.elastic.index.service.ElasticIndexClient;
import com.microservices.twitter_consumer_elastic_search.elastic.model.impl.TwitterIndexModel;
//import com.microservices.twitter_consumer_elastic_search.repository.TwitterElasticsearchIndexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
//public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);
//
//    private final TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository;
//
//    public TwitterElasticRepositoryIndexClient(TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository) {
//        this.twitterElasticsearchIndexRepository = twitterElasticsearchIndexRepository;
//    }
//
//    @Override
//    public List<String> save(List<TwitterIndexModel> documents) {
//        List<TwitterIndexModel> repositoryResponse =
//                (List<TwitterIndexModel>) twitterElasticsearchIndexRepository.saveAll(documents);
//        List<String> ids = repositoryResponse.stream().map(TwitterIndexModel::getId).toList();
//        LOGGER.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(), ids);
//        return ids;
//    }
//}
