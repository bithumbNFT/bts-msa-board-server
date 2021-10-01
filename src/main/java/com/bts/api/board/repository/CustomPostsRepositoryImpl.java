package com.bts.api.board.repository;

import com.bts.api.board.dto.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomPostsRepositoryImpl implements CustomPostsRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    //조회수 증가 쿼리
    @Override
    public Mono<Posts> viewPostAndUpdateViewCount(String id, int cnt) {
        Query query = new Query(Criteria.where("p_id").is(id));
        Update update = new Update().set("view_cnt", cnt);
        return mongoTemplate.findAndModify(query, update, Posts.class);
    }
}
