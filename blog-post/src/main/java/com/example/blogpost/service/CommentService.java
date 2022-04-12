package com.example.blogpost.service;

import com.example.blogpost.entity.Comment;
import com.example.blogpost.entity.Post;
import com.example.blogpost.exception.BadReplyException;
import com.example.blogpost.exception.CommentNotFoundException;
import com.example.blogpost.exception.PostNotFoundException;
import com.example.blogpost.repository.CommentRepository;
import com.example.blogpost.repository.PostRepository;
import com.example.blogpost.request.CommentCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getAllComments() {
        log.info("Get all comments");
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentsByPostId(Long postId) {
        log.info("Get all comments by post id: {}", postId);
        Post requestedPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        return commentRepository.findAllByPost(requestedPost);
    }

    public Comment getCommentById(Long commentId) {
        log.info("Get comment by id: {}", commentId);
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @Transactional
    public Comment createCommentForPostById(Long postId, CommentCreationRequest commentCreationRequest) {
        log.info("Create new comment for post by id: {}, request: {}", postId, commentCreationRequest);
        ZonedDateTime requestDateTime = ZonedDateTime.now();

        Post requestedPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Long replyToId = commentCreationRequest.getReplyToId();
        Comment parentComment = replyToId == null ?
                null :
                commentRepository.findById(replyToId)
                        .orElseThrow(() -> new CommentNotFoundException(replyToId));
        if (parentComment != null && !Objects.equals(parentComment.getPost(), requestedPost)) {
            throw new BadReplyException(parentComment.getPost(), requestedPost);
        }

        Comment comment = Comment.builder()
                .content(commentCreationRequest.getContent())
                .userIdCreated(commentCreationRequest.getUserId())
                .dateTimeCreated(requestDateTime)
                .dateTimeLastModified(requestDateTime)
                .post(requestedPost)
                .replyTo(parentComment)
                .replies(new ArrayList<>())
                .build();
        Comment savedComment = commentRepository.save(comment);

        if (parentComment != null) {
            log.info("Reply to: {}", parentComment);
            List<Comment> parentCommentReplies = parentComment.getReplies();
            parentCommentReplies.add(savedComment);
            parentComment.setReplies(parentCommentReplies);
            commentRepository.save(parentComment);
        }

        return savedComment;
    }

    @Transactional
    public Comment updateCommentById(Long commentId, String newContent) {
        log.info("Update comment by id: {}, new content: {}", commentId, newContent);
        ZonedDateTime requestDateTime = ZonedDateTime.now();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (newContent != null && newContent.length() > 0 && !Objects.equals(newContent, comment.getContent())) {
            comment.setContent(newContent);
            comment.setDateTimeLastModified(requestDateTime);
        }
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteCommentById(Long commentId) {
        log.info("Delete comment by id: {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new CommentNotFoundException(commentId));

        List<Comment> replies = comment.getReplies();
        for (Comment reply : replies) {
            deleteComment(reply);
        }

        Comment parentComment = comment.getReplyTo();
        if (parentComment != null) {
            List<Comment> parentCommentReplies = parentComment.getReplies();
            parentCommentReplies.remove(comment);
            parentComment.setReplies(parentCommentReplies);
        }

        commentRepository.deleteById(commentId);
    }

    private void deleteComment(Comment comment) {
        log.info("Delete comment: {}", comment);
        List<Comment> replies = comment.getReplies();
        for (Comment reply : replies) {
            deleteComment(reply);
        }
        commentRepository.delete(comment);
    }

}
