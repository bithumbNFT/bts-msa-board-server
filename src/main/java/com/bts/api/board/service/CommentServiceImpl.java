package com.bts.api.board.service;

import com.bts.api.board.dto.Comment;
import com.bts.api.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    @Override
    public Mono<Comment> save(Comment comment) {
        return commentRepository.save(comment);
    }
}
