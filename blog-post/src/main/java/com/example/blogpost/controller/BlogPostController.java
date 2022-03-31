package com.example.blogpost.controller;

import com.example.blogpost.entity.Post;
import com.example.blogpost.modelassembler.BlogPostModelAssembler;
import com.example.blogpost.request.PostCreationRequest;
import com.example.blogpost.service.PostService;
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

    private final PostService postService;
    private final BlogPostModelAssembler postModelAssembler;

    @Autowired
    public BlogPostController(PostService postService, BlogPostModelAssembler postModelAssembler) {
        this.postService = postService;
        this.postModelAssembler = postModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Post>> getAllPosts() {
        log.info("Request to get all posts");
        List<Post> posts = postService.getAllPosts();
        return postModelAssembler.toCollectionModel(posts);
    }

    @GetMapping(path = "{postId}")
    public EntityModel<Post> getPostById(@PathVariable(name = "postId") Long postId) {
        log.info("Request to get post by id: {}", postId);
        Post postById = postService.getPostById(postId);
        return postModelAssembler.toModel(postById);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Post> createPost(@RequestBody PostCreationRequest postCreationRequest) {
        log.info("Request to create new post: {}", postCreationRequest);
        Post createdPost = postService.createPost(postCreationRequest);
        return postModelAssembler.toModel(createdPost);
    }

    @PostMapping(path = "{postId}")
    public EntityModel<Post> updatePostById(@PathVariable(name = "postId") Long postId,
                                            @RequestParam(name = "title", required = false) String newTitle,
                                            @RequestParam(name = "content", required = false) String newContent) {
        log.info("Request to update post by id: {}", postId);
        Post updatedPost = postService.updatePostById(postId, newTitle, newContent);
        return postModelAssembler.toModel(updatedPost);
    }

    @DeleteMapping(path = "{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deletePostById(@PathVariable(name = "postId") Long postId) {
        log.info("Request to delete post by id: {}", postId);
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }

}
