package com.example.blogpost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BlogPostNotFoundException extends RuntimeException {

    public BlogPostNotFoundException(Long blogId) {
        super("Blog post by id " + blogId + " was not found.");
    }

}
