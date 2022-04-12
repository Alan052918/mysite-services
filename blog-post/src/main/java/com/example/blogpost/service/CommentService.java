package com.example.blogpost.service;

import com.example.blogpost.entity.Comment;
import com.example.blogpost.entity.Post;
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
    public Comment createComment(CommentCreationRequest commentCreationRequest) {
        log.info("Create new comment: {}", commentCreationRequest);
        ZonedDateTime requestDateTime = ZonedDateTime.now();

        Long requestedPostId = commentCreationRequest.getPostId();
        Post requestedPost = postRepository.findById(requestedPostId)
                .orElseThrow(() -> new PostNotFoundException(requestedPostId));

        Comment comment = Comment.builder()
                .content(commentCreationRequest.getContent())
                .userIdCreated(commentCreationRequest.getUserId())
                .userIdLastModified(commentCreationRequest.getUserId())
                .dateTimeCreated(requestDateTime)
                .dateTimeLastModified(requestDateTime)
                .post(requestedPost)
                .replyToId(commentCreationRequest.getReplyToId())
                .replyIds(new ArrayList<>())
                .build();
        Comment savedComment = commentRepository.save(comment);

        Long replyToId = commentCreationRequest.getReplyToId();
        if (replyToId != null) {
            Comment parentComment = commentRepository.findById(replyToId)
                    .orElseThrow(() -> new CommentNotFoundException(replyToId));
            List<Long> parentCommentReplyIds = parentComment.getReplyIds();
            parentCommentReplyIds.add(savedComment.getId());
            parentComment.setReplyIds(parentCommentReplyIds);
            commentRepository.save(parentComment);
        }

        return savedComment;
    }

    @Transactional
    public Comment updateCommentById(Long commentId, String newContent) {
        log.info("Update comment by id: {}, new content: {}", commentId, newContent);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (newContent != null && newContent.length() > 0 && !Objects.equals(newContent, comment.getContent())) {
            comment.setContent(newContent);
        }
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteCommentById(Long commentId) {
        log.info("Delete comment by id: {}", commentId);
        boolean existsById = commentRepository.existsById(commentId);
        if (!existsById) {
            throw new CommentNotFoundException(commentId);
        }
        commentRepository.deleteById(commentId);
    }

}
