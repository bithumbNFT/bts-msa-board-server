package com.bts.api.board.service;

import com.bts.api.board.dto.Posts;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface PostsService {
    Flux<Posts> findAll();
    Mono<Posts> findById(String id);
    Mono<Posts> save(Posts posts);
    Mono<Void> deleteById(String id);
    Mono<Void> delete(Posts posts);
}
