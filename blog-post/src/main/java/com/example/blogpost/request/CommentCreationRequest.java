package com.example.blogpost.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreationRequest {

    private Long postId;
    private Long replyToId;
    private Long userId;
//    private ZonedDateTime dateTime;
    private String content;

}
