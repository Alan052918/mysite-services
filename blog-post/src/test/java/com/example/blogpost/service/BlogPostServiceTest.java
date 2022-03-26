package com.example.blogpost.service;

import com.example.blogpost.entity.BlogPost;
import com.example.blogpost.exception.BlogPostNotFoundException;
import com.example.blogpost.repository.BlogPostDetailRepository;
import com.example.blogpost.repository.BlogPostRepository;
import com.example.blogpost.request.BlogPostCreationRequest;
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
class BlogPostServiceTest {

    private BlogPostService testService;
    private AutoCloseable autoCloseable;

    @Mock
    private BlogPostRepository mockBlogPostRepository;

    @Mock
    private BlogPostDetailRepository mockBlogPostDetailRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testService = new BlogPostService(mockBlogPostRepository, mockBlogPostDetailRepository);
    }

    @Test
    void GetAllBlogPosts_Success() {
        // when
        testService.getAllBlogPosts();

        // then
        verify(mockBlogPostRepository).findAll();
    }

    @Test
    @Disabled
    void getBlogPostById() {
    }

    @Test
    void CreateBlogPost_Success() {
        // given
        BlogPostCreationRequest testRequest = BlogPostCreationRequest.builder()
                .title("Hello")
                .content("Hello there, this is my first blog post.")
                .build();

        // when
        testService.createBlogPost(testRequest);

        // then;
        ArgumentCaptor<BlogPost> blogPostArgumentCaptor = ArgumentCaptor.forClass(BlogPost.class);
        verify(mockBlogPostRepository).save(blogPostArgumentCaptor.capture());
        BlogPost blogPostCaptorValue = blogPostArgumentCaptor.getValue();
        assertThat(blogPostCaptorValue.getTitle()).isEqualTo(testRequest.getTitle());
        assertThat(blogPostCaptorValue.getBlogPostDetail().getContent()).isEqualTo(testRequest.getContent());
    }

    @Test
    void UpdateBlogPostById_NonExistingBlogPostId_ExceptionThrown() {
        // given
        Long testId = 1L;

        // when
        // then
        assertThatThrownBy(() -> testService.updateBlogPostById(testId, null, null))
                .isInstanceOf(BlogPostNotFoundException.class)
                .hasMessageContaining("Blog post by id " + testId + " was not found.");
        verify(mockBlogPostRepository, never()).save(any());
    }

    @Test
    @Disabled
    void UpdateBlogPostById_ExistingBlogPostId_Success() {
        // given
        BlogPostCreationRequest testRequest = BlogPostCreationRequest.builder()
                .title("Hello")
                .content("Hello there, this is my first blog post.")
                .build();
        Long savedPostId = testService.createBlogPost(testRequest).getId();

        // when
        testService.updateBlogPostById(savedPostId, "Hello", "Hello, World!");

        // then
        verify(mockBlogPostRepository).save(any());
    }

    @Test
    @Disabled
    void deleteBlogPostById() {
        // TODO: implement this
    }
}
