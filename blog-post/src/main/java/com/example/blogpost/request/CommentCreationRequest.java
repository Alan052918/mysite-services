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

    private String content;
    private Long userId;
    private Long replyToId;
//    private ZonedDateTime dateTime;

}
