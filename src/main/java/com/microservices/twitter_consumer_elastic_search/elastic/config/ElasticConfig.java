package com.microservices.twitter_consumer_elastic_search.elastic.config;

import com.microservices.twitter_consumer_elastic_search.config.ElasticConfigData;
import com.microservices.twitter_consumer_elastic_search.exception.ElasticClientException;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

import javax.net.ssl.SSLContext;

@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.microservices.twitter_consumer_elastic_search.elastic.repository")
public class ElasticConfig extends ElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;
    private final static Logger LOGGER = LoggerFactory.getLogger(ElasticConfig.class);

    public ElasticConfig(ElasticConfigData elasticConfigData) {
        this.elasticConfigData = elasticConfigData;
    }

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        LOGGER.info("While creating the bean: {}",elasticConfigData);
        return ClientConfiguration.builder()
                .connectedTo(/*elasticConfigData.getConnectionUrl()*/"eks-srv:9200")
//                .usingSsl(buildSSLContext())
                .withConnectTimeout(elasticConfigData.getConnectionTimeoutMs())
                .withSocketTimeout(elasticConfigData.getSocketTimeoutMs())
                .build();
    }

    private SSLContext buildSSLContext() {
        try {
            return new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build();
        }catch (Exception e) {
            throw new ElasticClientException("Caught exception while creating SSL", e);
        }
    }
}
