package com.microservices.twitter_consumer_elastic_search.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.microservices.twitter_consumer_elastic_search.config.ElasticConfigData;
import com.microservices.twitter_consumer_elastic_search.exception.ElasticClientException;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Configuration
public class ElasticsearchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfig.class);

    private final ElasticConfigData elasticConfigData;

    public ElasticsearchConfig(ElasticConfigData elasticConfigData) {
        this.elasticConfigData = elasticConfigData;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        try (RestClient restClient = RestClient
                .builder(HttpHost.create(elasticConfigData.getConnectionUrl()))
                .build()) {
            ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            return new ElasticsearchClient(transport);
        }catch (Exception e) {
            LOGGER.error("Caught Exception: {} while building restclient", e.getMessage());
            throw new ElasticClientException(e.getMessage(), e);
        }
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(elasticsearchClient());
    }
}
