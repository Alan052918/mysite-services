package com.example.blogpost.service;

import com.example.blogpost.entity.BlogPost;
import com.example.blogpost.exception.BlogPostNotFoundException;
import com.example.blogpost.repository.BlogPostDetailRepository;
import com.example.blogpost.repository.BlogPostRepository;
import com.example.blogpost.request.BlogPostCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final BlogPostDetailRepository blogPostDetailRepository;

    @Autowired
    public BlogPostService(BlogPostRepository blogPostRepository, BlogPostDetailRepository blogPostDetailRepository) {
        this.blogPostRepository = blogPostRepository;
        this.blogPostDetailRepository = blogPostDetailRepository;
    }

    public List<BlogPost> getAllBlogPosts() {
        log.info("Get all blog posts");
        return blogPostRepository.findAll();
    }

    public BlogPost getBlogPostById(Long blogPostId) {
        log.info("Get blog post by id: {}", blogPostId);
        return blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new BlogPostNotFoundException(blogPostId));
    }

    public BlogPost createBlogPost(BlogPostCreationRequest blogPostCreationRequest) {
        // TODO: implement this
        return null;
    }

}
