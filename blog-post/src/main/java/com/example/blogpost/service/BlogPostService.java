package com.example.blogpost.service;

import com.example.blogpost.entity.BlogPost;
import com.example.blogpost.entity.BlogPostDetail;
import com.example.blogpost.exception.BlogPostDetailNotFoundException;
import com.example.blogpost.exception.BlogPostNotFoundException;
import com.example.blogpost.repository.BlogPostDetailRepository;
import com.example.blogpost.repository.BlogPostRepository;
import com.example.blogpost.request.BlogPostCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        //        BlogPost blogPostDetail = blogPostDetailRepository.findByBlogPost(blogPost)
//                .orElseThrow(() -> new BlogPostDetailNotFoundException(blogPost));
        return blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new BlogPostNotFoundException(blogPostId));
    }

    @Transactional
    public BlogPost createBlogPost(BlogPostCreationRequest blogPostCreationRequest) {
        log.info("Create new blog post: {}", blogPostCreationRequest);
        LocalDateTime requestDateTime = LocalDateTime.now();
        BlogPost blogPost = BlogPost.builder()
                .title(blogPostCreationRequest.getTitle())
                .dateTimeCreated(requestDateTime)
                .dateTimeUpdated(requestDateTime)
                .build();
        BlogPostDetail blogPostDetail = BlogPostDetail.builder()
                .blogPost(blogPost)
                .content(blogPostCreationRequest.getContent())
                .build();
        blogPostDetailRepository.save(blogPostDetail);
        return blogPostRepository.save(blogPost);
    }

    @Transactional
    public BlogPost updateBlogPostById(Long blogPostId, String newTitle, String newContent) {
        log.info("Update blog post by id: {}, new title: {}, new content: {}", blogPostId, newTitle, newContent);
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new BlogPostNotFoundException(blogPostId));
//        BlogPostDetail blogPostDetail = blogPost.getBlogPostDetail();
        BlogPostDetail blogPostDetail = blogPostDetailRepository.findByBlogPost(blogPost)
                .orElseThrow(() -> new BlogPostDetailNotFoundException(blogPost));
        if (newTitle != null && newTitle.length() > 0 && !Objects.equals(newTitle, blogPost.getTitle())) {
            blogPost.setTitle(newTitle);
        }
        if (newContent != null && newContent.length() > 0 && !Objects.equals(newContent, blogPostDetail.getContent())) {
            blogPostDetail.setContent(newContent);
        }
        blogPostDetailRepository.save(blogPostDetail);
        return blogPostRepository.save(blogPost);
    }

    @Transactional
    public void deleteBlogPostById(Long blogPostId) {
        log.info("Delete blog post by id: {}", blogPostId);
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new BlogPostNotFoundException(blogPostId));
//        BlogPostDetail blogPostDetail = blogPost.getBlogPostDetail();
        BlogPostDetail blogPostDetail = blogPostDetailRepository.findByBlogPost(blogPost)
                .orElseThrow(() -> new BlogPostDetailNotFoundException(blogPost));
        blogPostRepository.delete(blogPost);
        blogPostDetailRepository.delete(blogPostDetail);
    }

}
