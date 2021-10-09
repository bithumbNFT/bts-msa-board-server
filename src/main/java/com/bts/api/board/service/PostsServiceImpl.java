package com.bts.api.board.service;

import com.bts.api.board.dto.Posts;
import com.bts.api.board.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService{
    private final PostsRepository postsRepository;

    @Override
    public Flux<Posts> findAll() {
        return postsRepository.findAll();
    }
    @Override
    public Mono<Posts> findById(String id) {
        return postsRepository.findById(id);
    }

    @Override
    public Mono<Posts> save(Posts posts) {
        return postsRepository.save(posts);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return postsRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Posts posts) {
        return postsRepository.delete(posts);
    }
}
