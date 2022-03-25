package com.example.blogpost.controller;

import com.example.blogpost.entity.BlogPost;
import com.example.blogpost.entity.BlogPostDetail;
import com.example.blogpost.modelassembler.BlogPostModelAssembler;
import com.example.blogpost.request.BlogPostCreationRequest;
import com.example.blogpost.service.BlogPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/posts")
@Slf4j
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final BlogPostModelAssembler blogPostModelAssembler;

    @Autowired
    public BlogPostController(BlogPostService blogPostService, BlogPostModelAssembler blogPostModelAssembler) {
        this.blogPostService = blogPostService;
        this.blogPostModelAssembler = blogPostModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<BlogPost>> getAllBlogPosts() {
        log.info("Request to get all blog posts");
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        return blogPostModelAssembler.toCollectionModel(blogPosts);
    }

    @GetMapping(path = "{blogPostId}")
    public EntityModel<BlogPost> getBlogPostById(@PathVariable(name = "blogPostId") Long blogPostId) {
//        TODO: change to returning blogPostDetail entity model
        log.info("Request to get blog post by id: {}", blogPostId);
        BlogPost blogPostById = blogPostService.getBlogPostById(blogPostId);
        return blogPostModelAssembler.toModel(blogPostById);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<BlogPost> createBlogPost(@RequestBody BlogPostCreationRequest blogPostCreationRequest) {
        log.info("Request to create new blog post: {}", blogPostCreationRequest);
        BlogPost createdBlogPost = blogPostService.createBlogPost(blogPostCreationRequest);
        return blogPostModelAssembler.toModel(createdBlogPost);
    }

    @PostMapping(path = "{blogPostId}")
    public EntityModel<BlogPost> updateBlogPostById(@PathVariable(name = "blogPostId") Long blogPostId,
                                                    @RequestParam(name = "title", required = false) String newTitle,
                                                    @RequestParam(name = "content", required = false) String newContent) {
        log.info("Request to update blog post by id: {}", blogPostId);
        BlogPost updatedBlogPost = blogPostService.updateBlogPostById(blogPostId, newTitle, newContent);
        return blogPostModelAssembler.toModel(updatedBlogPost);
    }

    @DeleteMapping(path = "{blogPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteBlogPostById(@PathVariable(name = "blogPostId") Long blogPostId) {
        log.info("Request to delete blog post by id: {}", blogPostId);
        blogPostService.deleteBlogPostById(blogPostId);
        return ResponseEntity.noContent().build();
    }

}
