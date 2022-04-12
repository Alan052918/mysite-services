package com.example.blogpost.service;

import com.example.blogpost.entity.Post;
import com.example.blogpost.entity.PostDetail;
import com.example.blogpost.exception.PostDetailNotFoundException;
import com.example.blogpost.exception.PostNotFoundException;
import com.example.blogpost.repository.PostDetailRepository;
import com.example.blogpost.repository.PostRepository;
import com.example.blogpost.request.PostCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostDetailRepository postDetailRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostDetailRepository postDetailRepository) {
        this.postRepository = postRepository;
        this.postDetailRepository = postDetailRepository;
    }

    public List<Post> getAllPosts() {
        log.info("Get all posts");
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        log.info("Get post by id: {}", postId);
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Transactional
    public Post createPost(PostCreationRequest postCreationRequest) {
        log.info("Create new post: {}", postCreationRequest);
        ZonedDateTime requestDateTime = ZonedDateTime.now();

        Post post = Post.builder()
                .title(postCreationRequest.getTitle())
                .userIdCreated(postCreationRequest.getUserId())
                .userIdLastModified(postCreationRequest.getUserId())
                .dateTimeCreated(requestDateTime)
                .dateTimeLastModified(requestDateTime)
                .commentCount(0L)
                .likeCount(0L)
                .shareCount(0L)
                .build();

        PostDetail postDetail = PostDetail.builder()
                .content(postCreationRequest.getContent())
                .build();

        post.setPostDetail(postDetail);
        postDetail.setPost(post);
        postDetailRepository.save(postDetail);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePostById(Long postId, String newTitle, String newContent) {
        log.info("Update post by id: {}, new title: {}, new content: {}", postId, newTitle, newContent);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        PostDetail postDetail = postDetailRepository.findByPost(post)
                .orElseThrow(() -> new PostDetailNotFoundException(post));
        if (newTitle != null && newTitle.length() > 0 && !Objects.equals(newTitle, post.getTitle())) {
            post.setTitle(newTitle);
        }
        if (newContent != null && newContent.length() > 0 && !Objects.equals(newContent, postDetail.getContent())) {
            postDetail.setContent(newContent);
        }
        postDetailRepository.save(postDetail);
        return postRepository.save(post);
    }

    @Transactional
    public void deletePostById(Long postId) {
        log.info("Delete post by id: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        PostDetail postDetail = postDetailRepository.findByPost(post)
                .orElseThrow(() -> new PostDetailNotFoundException(post));
        postRepository.delete(post);
        postDetailRepository.delete(postDetail);
    }

}
