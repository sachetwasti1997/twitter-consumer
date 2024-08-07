package com.microservices.twitter_consumer_elastic_search.exception;

public class ElasticClientException extends RuntimeException{
    public ElasticClientException() {
    }

    public ElasticClientException(String message) {
        super(message);
    }

    public ElasticClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
