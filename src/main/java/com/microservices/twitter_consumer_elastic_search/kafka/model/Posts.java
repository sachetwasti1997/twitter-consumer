package com.microservices.twitter_consumer_elastic_search.kafka.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Posts {
    private Long userId;
    @Id
    private String id;
    private String text;
    private String createdAt;
}
