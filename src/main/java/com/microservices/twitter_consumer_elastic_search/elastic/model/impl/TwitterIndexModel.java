package com.microservices.twitter_consumer_elastic_search.elastic.model.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Document(indexName = "#{elasticConfigData.indexName}")
public class TwitterIndexModel implements IndexModel {

    @JsonProperty
    private String id;
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String text;

    @Field(type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonProperty
    private LocalDateTime createdAt;
}
