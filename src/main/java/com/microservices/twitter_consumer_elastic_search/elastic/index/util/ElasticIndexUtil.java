package com.microservices.twitter_consumer_elastic_search.elastic.index.util;

import com.microservices.twitter_consumer_elastic_search.elastic.model.IndexModel;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticIndexUtil<T extends IndexModel> {

    public IndexQuery getIndexQueries(T documents) {
        return new IndexQueryBuilder()
                        .withId(documents.getId())
                        .withObject(documents)
                        .build();
    }

}
