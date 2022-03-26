package com.example.blogpost.repository;

import com.example.blogpost.entity.BlogPost;
import com.example.blogpost.entity.BlogPostDetail;
import com.example.blogpost.exception.BlogPostDetailNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class BlogPostDetailRepositoryTest {

    @Mock
    private BlogPostRepository mockBlogPostRepository;

    @Autowired
    private BlogPostDetailRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void FindByBlogPost_SavedBlogPost_NotNull() {
        // given
        BlogPost testPost = BlogPost.builder().build();
        BlogPostDetail testPostDetail = BlogPostDetail.builder()
                .blogPost(testPost)
                .build();
        testPost.setBlogPostDetail(testPostDetail);
        testRepository.save(testPostDetail);

        // when
        BlogPostDetail foundBlogPostDetail = testRepository.findByBlogPost(testPost).orElse(null);

        // then
        assertThat(foundBlogPostDetail).isNotNull();
    }

    @Test
    void FindByBlogPost_UnsavedBlogPost_ExceptionThrown() {
        // given
        BlogPost testPost = BlogPost.builder().build();
        BlogPostDetail testPostDetail = BlogPostDetail.builder()
                .blogPost(testPost)
                .build();
        testPost.setBlogPostDetail(testPostDetail);
        mockBlogPostRepository.save(testPost);

        // when
        // then
        assertThatThrownBy(() -> testRepository.findByBlogPost(testPost)
                .orElseThrow(() -> new BlogPostDetailNotFoundException(testPost)))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

}
