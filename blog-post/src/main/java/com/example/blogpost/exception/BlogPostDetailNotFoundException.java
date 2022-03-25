package com.example.blogpost.exception;

import com.example.blogpost.entity.BlogPost;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BlogPostDetailNotFoundException extends RuntimeException {

    public BlogPostDetailNotFoundException(BlogPost blogPost) {
        super("Blog post detail by blog post " + blogPost.toString() + " was not found.");
    }

}
