package com.example.blogpost.exception;

import com.example.blogpost.entity.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostDetailNotFoundException extends RuntimeException {

    public PostDetailNotFoundException(Post post) {
        super("Blog post detail by blog post " + post.toString() + " was not found.");
    }

}
