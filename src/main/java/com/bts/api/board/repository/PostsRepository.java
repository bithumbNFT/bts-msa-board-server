package com.bts.api.board.repository;

import com.bts.api.board.dto.Posts;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostsRepository extends ReactiveMongoRepository<Posts, String> {
    //제목으로 게시물 조회
    @Query("{'title': {$regex: ?0}}")
    Flux<Posts> findRegexByTitles(String title);

    //내용으로 게시물 검색
    @Query("{'content': {$regex: ?0}}")
    Flux<Posts> findRegexByContents(String content);

    //작성자로 게시물 검색
    @Query("{'author': {$regex: ?0}}")
    Flux<Posts> findRegexByAuthor(String author);
}
