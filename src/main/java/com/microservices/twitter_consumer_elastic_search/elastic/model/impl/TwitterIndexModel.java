package com.microservices.twitter_consumer_elastic_search.elastic.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.twitter_consumer_elastic_search.elastic.model.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Builder
@Document(indexName = "#{@elasticConfigData.getIndexName()}")
public class TwitterIndexModel implements IndexModel {
    @JsonProperty
    private String id;
    @JsonProperty
    private String text;
    @JsonProperty
    private String createdAt;
    @JsonProperty
    private Long userId;
}
