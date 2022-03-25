package com.example.blogpost.controller;

import com.example.blogpost.entity.BlogPost;
import com.example.blogpost.model_assembler.BlogPostModelAssembler;
import com.example.blogpost.request.BlogPostCreationRequest;
import com.example.blogpost.service.BlogPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
        log.info("Request to get blog post by id: {}", blogPostId);
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        return blogPostModelAssembler.toModel(blogPost);
    }

    @PostMapping(path = "draft")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<BlogPost> createBlogPost(@RequestBody BlogPostCreationRequest blogPostCreationRequest) {
        log.info("Request to create new blog post: {}", blogPostCreationRequest);
        BlogPost blogPost = blogPostService.createBlogPost(blogPostCreationRequest);
        return blogPostModelAssembler.toModel(blogPost);
    }

    @PostMapping(path = "{blogPostId}")
    public EntityModel<BlogPost> updateBlogPost(@PathVariable(name = "blogPostId") Long blogPostId,
                                                @RequestParam(name = "title", required = false) String newTitle,
                                                @RequestParam(name = "content", required = false) String newContent) {
        // TODO: implement this
        return null;
    }

    @DeleteMapping(path = "{blogPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(@PathVariable(name = "blogPostId") Long blogPostId) {
        // TODO: implement this
    }

}
