package com.example.blogpost.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreationRequest {

    private String title;
    private Long userId;
    private LocalDateTime dateTime;
    private String content;

}
