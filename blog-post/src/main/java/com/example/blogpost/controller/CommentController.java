package com.example.blogpost.controller;

import com.example.blogpost.entity.Comment;
import com.example.blogpost.modelassembler.CommentModelAssembler;
import com.example.blogpost.request.CommentCreationRequest;
import com.example.blogpost.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentModelAssembler commentModelAssembler;

    @Autowired
    public CommentController(CommentService commentService, CommentModelAssembler commentModelAssembler) {
        this.commentService = commentService;
        this.commentModelAssembler = commentModelAssembler;
    }

    @GetMapping(path = "posts/{postId}/comments")
    public CollectionModel<EntityModel<Comment>> getAllCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        log.info("Request to get all comments by post id: {}", postId);
        List<Comment> commentsByPostId = commentService.getAllCommentsByPostId(postId);
        return commentModelAssembler.toCollectionModel(commentsByPostId);
    }

    @GetMapping(path = "comments")
    public CollectionModel<EntityModel<Comment>> getAllComments() {
        log.info("Request to get all comments");
        List<Comment> comments = commentService.getAllComments();
        return commentModelAssembler.toCollectionModel(comments);
    }

    @GetMapping(path = "comments/{commentId}")
    public EntityModel<Comment> getCommentById(@PathVariable(name = "commentId") Long commentId) {
        log.info("Request to get comment by id: {}", commentId);
        Comment commentById = commentService.getCommentById(commentId);
        return commentModelAssembler.toModel(commentById);
    }

    @PostMapping(path = "posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Comment> createComment(@PathVariable(name = "postId") Long postId,
                                              @RequestBody CommentCreationRequest commentCreationRequest) {
        log.info("Request to create new comment for post by id: {}, request: {}", postId, commentCreationRequest);
        Comment createdComment = commentService.createCommentForPostById(postId, commentCreationRequest);
        return commentModelAssembler.toModel(createdComment);
    }

    @PostMapping(path = "comments/{commentId}")
    public EntityModel<Comment> updateCommentById(@PathVariable(name = "commentId") Long commentId,
                                                  @RequestParam(name = "content") String newContent) {
        log.info("Request to update comment by id: {}, new content: {}", commentId, newContent);
        Comment updatedComment = commentService.updateCommentById(commentId, newContent);
        return commentModelAssembler.toModel(updatedComment);
    }

    @DeleteMapping(path = "comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteCommentById(@PathVariable(name = "commentId") Long commentId) {
        log.info("Request to delete comment by id: {}", commentId);
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

}
