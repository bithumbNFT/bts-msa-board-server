package com.bts.api.board.service;

import com.bts.api.board.dto.Comment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface CommentService {
    Mono<Comment> save(Comment comment);
}
