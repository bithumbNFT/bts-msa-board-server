package com.bts.api.board.controller;

import com.bts.api.board.dto.Posts;
import com.bts.api.board.repository.CommentRepository;
import com.bts.api.board.repository.PostsRepository;
import com.bts.api.board.service.CommentService;
import com.bts.api.board.service.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PostsController.class)
@Import(PostsRepository.class)
class PostsControllerTest {
    @MockBean
    PostsRepository postsRepository;
    @MockBean
    PostsService postsService;
    @MockBean
    CommentRepository commentRepository;
    @Autowired
    private WebTestClient webTestClient;

    private Posts posts;

    @BeforeEach
    void setUp() {
        //게시물 데이터 초기화
        posts = new Posts();
        posts.setP_id("init_post_id");
        posts.setAuthor("작성자");
        posts.setTitle("기존 제목");
        posts.setContent("기존 내용");
        posts.setCreatePostDate(LocalDateTime.now());
        posts.setModifiedPostDate(LocalDateTime.now());

    }

    @DisplayName("모든 게시물 조회 테스트")
    @Test
    void getAllPostsTest() {
        //준비
        List<Posts> postsList = new ArrayList<>();
        postsList.add(posts);
        Flux<Posts> postsFlux = Flux.fromIterable(postsList);
        //실행
        Mockito
                .when(postsService.findAll())
                .thenReturn(postsFlux);
        //단언
        webTestClient.get()
                .uri("/board")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Posts.class);
        Mockito
                .verify(postsService, Mockito.times(1))
                .findAll();
    }

    @DisplayName("단일 게시물 조회 테스트")
    @Test
    void getThePostTest() {
        //실행
        Mockito
                .when(postsService.findById("init_post_id"))
                .thenReturn(Mono.just(posts));
        //단언
        webTestClient.get()
                .uri("/post/{p_id}", "init_post_id")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                //.jsonPath("$.author").isNotEmpty()
                .jsonPath("$.p_id").isEqualTo("init_post_id")
                .jsonPath("$.author").isEqualTo("작성자")
                .jsonPath("$.title").isEqualTo("기존 제목")
                .jsonPath("$.content").isEqualTo("기존 내용");

    }

    @DisplayName("게시물 생성 테스트")
    @Test
    void createThePostTest() {
        //실행
        Mockito
                .when(postsService.save(posts))
                .thenReturn(Mono.just(posts));
        //단언
        webTestClient.post()
                .uri("/write")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(posts), Posts.class)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    void updateThePostTest() {
        //준비 - setUp 데이터 save
        Mockito
                .when(postsService.save(posts))
                .thenReturn(Mono.just(posts));
        //실행
        Mockito
                .when(postsService.findById("init_post_id"))
                .thenReturn(Mono.just(posts));
        //기존 데이터 값 변경
        posts.setTitle("변경된 제목");
        posts.setContent("변경된 내용");
        posts.setModifiedPostDate(LocalDateTime.now());
        Mockito
                .when(postsService.save(posts))
                .thenReturn(Mono.just(posts));
        //단언
        webTestClient.put()
                .uri("/post/{p_id}", "init_post_id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(posts), Posts.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.p_id").isEqualTo("init_post_id")
                .jsonPath("$.author").isEqualTo("작성자")
                .jsonPath("$.title").isEqualTo("변경된 제목")
                .jsonPath("$.content").isEqualTo("변경된 내용");
    }

    @DisplayName("게시물 삭제 테스트")
    @Test
    void deleteThePostTest() {
        //준비
        Mono<Void> voidReturn = Mono.empty();
        //실행
        Mockito
                .when(postsService.deleteById("init_post_id"))
                .thenReturn(voidReturn);
        //단언
        webTestClient.get()
                .uri("/post/{p_id}", "init_post_id")
                .exchange()
                .expectStatus().isOk();
    }
}