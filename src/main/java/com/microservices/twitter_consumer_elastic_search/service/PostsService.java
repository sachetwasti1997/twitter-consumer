package com.microservices.twitter_consumer_elastic_search.service;

import com.microservices.twitter_consumer_elastic_search.kafka.model.Posts;
import com.microservices.twitter_consumer_elastic_search.kafka.model.TwitterAvroModel;
import com.microservices.twitter_consumer_elastic_search.repository.PostsRepository;
import org.springframework.stereotype.Service;

@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public String save(Posts posts) {
        var post = postsRepository.save(posts);
        return post.getId();
    }
}
