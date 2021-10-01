package com.bts.api.board.controller;

import com.bts.api.board.dto.Comment;
import com.bts.api.board.dto.Posts;
import com.bts.api.board.repository.CommentRepository;
import com.bts.api.board.repository.PostsRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PostsController.class)
@Import(PostsRepository.class)
class CommentControllerTest {
    @MockBean
    PostsRepository postsRepository;
    @MockBean
    PostsService postsService;
    @MockBean
    CommentRepository commentRepository;
    @Autowired
    private WebTestClient webTestClient;

    private Posts posts;
    private Comment comment;
    private ArrayList<Comment> comments;

    @BeforeEach
    void setUp() {
        //댓글 초기 데이터
        comment = new Comment();
        comment.setC_id("init_comment_id");
        comment.setCommentWriter("댓글 작성자");
        comment.setCommentContent("댓글 내용");
        comment.setCreatePostDate(LocalDateTime.now());

        //댓글을 등록 할 자료구조 초기화
        comments = new ArrayList<>();
        //게시물 초기 데이터
        posts = new Posts();
        posts.setP_id("init_post_id");
        posts.setAuthor("작성자");
        posts.setTitle("기존 제목");
        posts.setContent("기존 내용");
        posts.setCommentList(comments);
        posts.setCreatePostDate(LocalDateTime.now());
        posts.setModifiedPostDate(LocalDateTime.now());
    }

    @Test
    @DisplayName("기존 게시물에 댓글 등록 테스트")
    void postTheCommentTest() {
        //1. 준비: 댓글 포스트할 게시물 찾기
        Mockito
                .when(postsService.findById("init_post_id"))
                .thenReturn(Mono.just(posts));
        //실행
        //2. 댓글 저장
        posts.setCommentList(comments, comment);
        Mockito.when(postsService.save(posts))
                .thenReturn(Mono.just(posts));

        //단언
        webTestClient.put()
                .uri("/post/{p_id}", "init_post_id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(posts), Posts.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("기존 게시물에 댓글 조회 테스트")
    void getTheCommentsInsideAPostTest() {
        //1. 준비: 댓글 포스트할 게시물 찾기
        Mockito
                .when(postsService.findById("init_post_id"))
                .thenReturn(Mono.just(posts));
        //실행
        //2. 댓글 저장
        posts.setCommentList(comments, comment);
        Mockito.when(postsService.save(posts))
                .thenReturn(Mono.just(posts));

        //단언
        webTestClient.get()
                .uri("/post/{p_id}", "init_post_id")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.p_id").isEqualTo("init_post_id")
                .jsonPath("$.comment_list[0].c_id").isEqualTo("init_comment_id")
                .jsonPath("$.comment_list[0].comment_writer").isEqualTo("댓글 작성자")
                .jsonPath("$.comment_list[0].comment_content").isEqualTo("댓글 내용");
    }

    @Test
    @DisplayName("기존 게시물에 댓글 삽입 및 삭제 테스트")
    void deleteTheCommentTest() {
        /* 준비 */
        //새 댓글 작성
        Comment comment2 = new Comment();
        comment2.setC_id("init_comment_id2");
        comment2.setCommentWriter("댓글 작성자2");
        comment2.setCommentContent("댓글 내용");
        comment2.setCreatePostDate(LocalDateTime.now());

        Comment comment3 = new Comment();
        comment3.setC_id("init_comment_id3");
        comment3.setCommentWriter("댓글 작성자3");
        comment3.setCommentContent("댓글 내용");
        comment3.setCreatePostDate(LocalDateTime.now());

        //저장할 포스트 찾기
        Mockito
                .when(postsService.findById("init_post_id"))
                .thenReturn(Mono.just(posts));

        //새 댓글 저장
        //댓글 2
        posts.setCommentList(comments, comment2);
        Mockito
                .when(postsService.save(posts))
                .thenReturn(Mono.just(posts));
        //댓글 3
        posts.setCommentList(comments, comment3);
        Mockito
                .when(postsService.save(posts))
                .thenReturn(Mono.just(posts));

        /* 실행 */
        //댓글 2 삭제
        posts.deleteCommentList(comments, comments.indexOf(comment2));
        Mockito
                .when(postsService.save(posts))
                .thenReturn(Mono.just(posts));

        /* 단언 */
        webTestClient.put()
                .uri("/post/{p_id}/", "init_post_id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(posts), Posts.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.p_id").isEqualTo("init_post_id")
                .jsonPath("$.comment_list[0].c_id").isEqualTo("init_comment_id3")
                .jsonPath("$.comment_list[0].comment_writer").isEqualTo("댓글 작성자3")
                .jsonPath("$.comment_list[0].comment_content").isEqualTo("댓글 내용");
    }
}