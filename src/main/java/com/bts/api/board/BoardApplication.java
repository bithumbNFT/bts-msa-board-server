package com.bts.api.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class BoardApplication {
//    private final PostsRepository posts;
//    private final CommentRepository comment;
//
//    public BoardApplication(PostsRepository posts, CommentRepository comment) {
//        this.posts = posts;
//        this.comment = comment;
//    }

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        this.comment.deleteAll();
//        this.posts.deleteAll()
//                .thenMany(Flux.just("테스트 게시물 1", "테스트 게시물 2")
//                        .flatMap(
//                                title -> this.posts.save(
//                                        Posts.builder()
//                                                .title(title)
//                                                .author("게시물 등록자")
//                                                .content("Content of " + title)
//                                                .build())
//
//                        )
//                )
//                .thenMany(
//                        this.posts.findAll()
//                )
//                .subscribe(
//                        data -> log.info("found posts: {}", posts),
//                        error -> log.error("", error),
//                        () -> log.info("초기화 완료...")
//                );
//    }
}
