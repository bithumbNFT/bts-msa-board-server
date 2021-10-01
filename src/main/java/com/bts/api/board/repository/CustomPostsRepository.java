package com.bts.api.board.repository;

import com.bts.api.board.dto.Posts;
import reactor.core.publisher.Mono;

public interface CustomPostsRepository {
    Mono<Posts> viewPostAndUpdateViewCount(String id, int cnt);
}
