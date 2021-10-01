package com.bts.api.board.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiParam;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Document(value = "posts")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Posts implements Serializable {
    @ApiParam(value = "id", required = true)
    private @Id
    String p_id;
    @ApiParam(value = "작성자", required = true)
    private String author;
    @ApiParam(value = "제목", required = true)
    private String title;
    @ApiParam(value = "내용", required = true)
    private String content;

    @ApiParam(value = "게시물 생성 시간", required = true)
    @CreatedDate
    @Builder.Default
    private LocalDateTime createPostDate = LocalDateTime.now();

    @ApiParam(value = "게시물 수정 시간", required = true)
    @LastModifiedDate
    @Builder.Default
    private LocalDateTime modifiedPostDate = LocalDateTime.now();

    @ApiParam(value = "댓글 리스트", required = true)
    @Builder.Default
    private ArrayList<Comment> commentList = new ArrayList<>();

    @Builder
    public Posts(@NonNull String author, @NonNull String title, @NonNull String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList, Comment comment) {
        this.commentList = commentList;
        this.commentList.add(comment);
    }

    //바로 들어온 댓글은 바로 삭제를 할 수 없다. 다른 댓글을 추가 시켜야 삭제하려했던 댓글을 삭제할 수 있다.
    public void deleteCommentList(ArrayList<Comment> commentList, int index) {
        this.commentList = commentList;
        this.commentList.remove(index);
    }
}
