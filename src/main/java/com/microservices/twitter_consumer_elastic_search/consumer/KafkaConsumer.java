package com.microservices.twitter_consumer_elastic_search.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;
import java.util.List;

public interface KafkaConsumer<K extends Serializable, V extends SpecificRecordBase> {
    public void receive(List<V> messages, List<Long> keys, List<Integer> partition, List<Long> offsets);
}
