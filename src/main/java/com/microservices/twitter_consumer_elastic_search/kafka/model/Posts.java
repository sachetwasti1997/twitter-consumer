package com.microservices.twitter_consumer_elastic_search.kafka.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Posts {
    private Long userId;
    private String id;
    private String text;
    private String createdAt;
}
