package com.bts.api.board.repository;

import com.bts.api.board.dto.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
