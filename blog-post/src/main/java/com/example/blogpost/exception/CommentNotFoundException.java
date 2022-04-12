package com.example.blogpost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long commentId) {
        super("Comment by id " + commentId + " was not found.");
    }

}
