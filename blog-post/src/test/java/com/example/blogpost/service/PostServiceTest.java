package com.example.blogpost.service;

import com.example.blogpost.entity.Post;
import com.example.blogpost.exception.PostNotFoundException;
import com.example.blogpost.repository.PostDetailRepository;
import com.example.blogpost.repository.PostRepository;
import com.example.blogpost.request.PostCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private PostService testService;
    private AutoCloseable autoCloseable;

    @Mock
    private PostRepository mockPostRepository;

    @Mock
    private PostDetailRepository mockPostDetailRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testService = new PostService(mockPostRepository, mockPostDetailRepository);
    }

    @Test
    void GetAllBlogPosts_Success() {
        // when
        testService.getAllPosts();

        // then
        verify(mockPostRepository).findAll();
    }

    @Test
    @Disabled
    void getBlogPostById() {
    }

    @Test
    void CreateBlogPost_Success() {
        // given
        PostCreationRequest testRequest = PostCreationRequest.builder()
                .title("Hello")
                .content("Hello there, this is my first blog post.")
                .build();

        // when
        testService.createPost(testRequest);

        // then;
        ArgumentCaptor<Post> blogPostArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(mockPostRepository).save(blogPostArgumentCaptor.capture());
        Post postCaptorValue = blogPostArgumentCaptor.getValue();
        assertThat(postCaptorValue.getTitle()).isEqualTo(testRequest.getTitle());
        assertThat(postCaptorValue.getPostDetail().getContent()).isEqualTo(testRequest.getContent());
    }

    @Test
    void UpdateBlogPostById_NonExistingBlogPostId_ExceptionThrown() {
        // given
        Long testId = 1L;

        // when
        // then
        assertThatThrownBy(() -> testService.updatePostById(testId, null, null))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessageContaining("Blog post by id " + testId + " was not found.");
        verify(mockPostRepository, never()).save(any());
    }

    @Test
    @Disabled
    void UpdateBlogPostById_ExistingBlogPostId_Success() {
        // given
        PostCreationRequest testRequest = PostCreationRequest.builder()
                .title("Hello")
                .content("Hello there, this is my first blog post.")
                .build();
        Long savedPostId = testService.createPost(testRequest).getId();

        // when
        testService.updatePostById(savedPostId, "Hello", "Hello, World!");

        // then
        verify(mockPostRepository).save(any());
    }

    @Test
    @Disabled
    void deleteBlogPostById() {
        // TODO: implement this
    }
}
