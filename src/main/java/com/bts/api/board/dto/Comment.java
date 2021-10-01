package com.bts.api.board.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Comment implements Serializable {
    @ApiParam(value = "댓글 id", required = true)
    private @Id
    String c_id;
    @ApiParam(value = "댓글 작성자", required = true)
    private String commentWriter;
    @ApiParam(value = "댓글 내용", required = true)
    private String commentContent;

    @ApiParam(value = "댓글 생성 시간", required = true)
    @CreatedDate
    @Builder.Default
    private LocalDateTime createPostDate = LocalDateTime.now();

    @ApiParam(value = "댓글 수정 시간", required = true)
    @LastModifiedDate
    @Builder.Default
    private LocalDateTime modifiedPostDate = LocalDateTime.now();

    @Builder
    public Comment(@NonNull String commentWriter, @NonNull String commentContent) {
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
    }
}