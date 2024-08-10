package com.microservices.twitter_consumer_elastic_search.elastic.config;

import com.microservices.twitter_consumer_elastic_search.config.ElasticConfigData;
import com.microservices.twitter_consumer_elastic_search.exception.ElasticClientException;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.microservices.twitter_consumer_elastic_search.elastic.repository")
public class ElasticConfig extends ElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;

    public ElasticConfig(ElasticConfigData elasticConfigData) {
        this.elasticConfigData = elasticConfigData;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(/*elasticConfigData.getConnectionUrl()*/"eks-srv:9200")
//                .usingSsl(buildSSLContext())
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
