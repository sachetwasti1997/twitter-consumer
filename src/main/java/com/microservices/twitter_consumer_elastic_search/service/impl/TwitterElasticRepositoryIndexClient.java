package com.microservices.twitter_consumer_elastic_search.service.impl;

import com.microservices.twitter_consumer_elastic_search.elastic.model.impl.TwitterIndexModel;
import com.microservices.twitter_consumer_elastic_search.elastic.repository.TwitterModelRepository;
import com.microservices.twitter_consumer_elastic_search.service.ElasticIndexClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);

    private final TwitterModelRepository twitterModelRepository;

    public TwitterElasticRepositoryIndexClient(TwitterModelRepository twitterModelRepository) {
        this.twitterModelRepository = twitterModelRepository;
    }

    @Override
    public String save(TwitterIndexModel document) {
        TwitterIndexModel model = twitterModelRepository.save(document);
        LOGGER.info("Saved successfully with id: {}", model.getId());
        return model.getId();
    }
}
