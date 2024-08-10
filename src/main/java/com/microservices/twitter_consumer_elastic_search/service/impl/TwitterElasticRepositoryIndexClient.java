package com.microservices.twitter_consumer_elastic_search.service.impl;

import com.microservices.twitter_consumer_elastic_search.config.ElasticConfigData;
import com.microservices.twitter_consumer_elastic_search.elastic.model.impl.TwitterIndexModel;
import com.microservices.twitter_consumer_elastic_search.elastic.repository.TwitterModelRepository;
import com.microservices.twitter_consumer_elastic_search.elastic.util.ElasticIndexUtil;
import com.microservices.twitter_consumer_elastic_search.service.ElasticIndexClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);

    private final TwitterModelRepository twitterModelRepository;
    private final ElasticConfigData elasticConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    public TwitterElasticRepositoryIndexClient(TwitterModelRepository twitterModelRepository, ElasticConfigData elasticConfigData, ElasticsearchOperations elasticsearchOperations, ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil) {
        this.twitterModelRepository = twitterModelRepository;
        this.elasticConfigData = elasticConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticIndexUtil = elasticIndexUtil;
    }

    @Override
    public String save(TwitterIndexModel document) {
        List<IndexQuery> elastic = List.of(new IndexQueryBuilder()
                .withId(document.getId())
                .withObject(document)
                .build());
        List<IndexedObjectInformation> documentIds = elasticsearchOperations.bulkIndex(elastic, IndexCoordinates.of(elasticConfigData.getIndexName()));
//        TwitterIndexModel model = twitterModelRepository.save(document);
        LOGGER.info("Saved successfully with id: {}", documentIds);
        return documentIds.get(0).id();
    }
}
