package com.microservices.twitter_consumer_elastic_search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "retry-config")
public class RetryConfigData {
    private Long maxIntervalMs;
    private Double multiplier;
    private Short maxAttempts;
    private Long initialIntervalMs;
    private Long sleepTimeMs;
}
