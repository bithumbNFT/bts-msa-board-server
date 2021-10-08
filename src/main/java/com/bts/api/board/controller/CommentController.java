package com.bts.api.board.controller;

import com.bts.api.board.dto.Comment;
import com.bts.api.board.dto.Posts;
import com.bts.api.board.service.CommentService;
import com.bts.api.board.service.PostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(value = "CommentController")
public class CommentController {
    private final PostsService postsService;
    private final CommentService commentService;

    @ApiOperation(value = "댓글 작성하기")
    @RequestMapping(value = "/post/{p_id}", method = RequestMethod.POST)
    public Mono<ResponseEntity<Posts>> postTheComment(@PathVariable(value = "p_id") String p_id,
                                                      @RequestBody Comment comment) {
        //1. 댓글을 먼저 comment db 에 저장
        //2. 매핑된 url로 댓글을 등록할 post 를 찾고, post 도메인의 commentList 에 댓글을 저장한다.
        log.debug("[CommentController] {"+p_id+"}에 댓글 작성");
        return this.commentService.save(comment)
                .then(this.postsService.findById(p_id)
                        .flatMap(i -> {
                            i.setCommentList(i.getCommentList(), comment);
                            return this.postsService.save(i);
                        })
                        .map(ResponseEntity::ok)
                        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @ApiOperation(value = "댓글 삭제하기")
    @RequestMapping(value = "/post/{p_id}/delete_comment", method = RequestMethod.PUT)
    public Mono<ResponseEntity<Posts>> deleteTheComment(@PathVariable(value = "p_id") String p_id,
                                        @RequestBody Comment comment) {
        log.debug("[CommentController] {"+p_id+"}의 댓글 삭제");
        return this.postsService.findById(p_id)
                .flatMap(i -> {
                    i.deleteCommentList(i.getCommentList(), i.getCommentList().indexOf(comment));
                    return this.postsService.save(i);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
