package com.example.blog_post.service;

import com.example.blog_post.entity.BlogPost;
import com.example.blog_post.entity.BlogPostDetail;
import com.example.blog_post.exception.BlogPostNotFoundException;
import com.example.blog_post.repository.BlogPostDetailRepository;
import com.example.blog_post.repository.BlogPostRepository;
import com.example.blog_post.request.BlogPostCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
