package com.microservices.twitter_consumer_elastic_search.elastic.util;

import com.microservices.twitter_consumer_elastic_search.elastic.model.IndexModel;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import java.util.List;

public class ElasticIndexUtil <T extends IndexModel>{
    public List<IndexQuery> getIndexQueries(List<T> documents) {
        return documents.stream()
                .map(document -> new IndexQueryBuilder()
                                .withId(document.getId())
                                .withObject(document)
                                .build()
                ).toList();
    }
}
