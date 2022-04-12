package com.example.blogpost.exception;

import com.example.blogpost.entity.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadReplyException extends RuntimeException {

    public BadReplyException(Post parentPost, Post requestedPost) {
        super("Parent comment to reply to is under different post " + parentPost.toString() + " than requested post " + requestedPost.toString());
    }

}
