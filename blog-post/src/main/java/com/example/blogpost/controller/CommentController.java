package com.example.blogpost.controller;

import com.example.blogpost.entity.Comment;
import com.example.blogpost.request.CommentCreationRequest;
import com.example.blogpost.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "posts/{postId}/comments")
    public List<Comment> getAllCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        log.info("Request to get all comments by post id: {}", postId);
        return commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping(path = "comments/{commentId}")
    public Comment getCommentById(@PathVariable(name = "commentId") Long commentId) {
        log.info("Request to get comment by id: {}", commentId);
        return commentService.getCommentById(commentId);
    }

    @PostMapping(path = "comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody CommentCreationRequest commentCreationRequest) {
        log.info("Request to create new comment: {}", commentCreationRequest);
        return commentService.createComment(commentCreationRequest);
    }

    @PostMapping(path = "comments/{commentId}")
    public Comment updateCommentById(@PathVariable(name = "commentId") Long commentId,
                                     @RequestParam(name = "content") String newContent) {
        return commentService.updateCommentById(commentId, newContent);
    }

    @DeleteMapping(path = "comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable(name = "commentId") Long commentId) {
        log.info("Request to delete comment by id: {}", commentId);
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

}
