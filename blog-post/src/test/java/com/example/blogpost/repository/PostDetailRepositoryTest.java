package com.example.blogpost.repository;

import com.example.blogpost.entity.Post;
import com.example.blogpost.entity.PostDetail;
import com.example.blogpost.exception.PostDetailNotFoundException;
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
class PostDetailRepositoryTest {

    @Mock
    private PostRepository mockPostRepository;

    @Autowired
    private PostDetailRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void FindByBlogPost_SavedBlogPost_NotNull() {
        // given
        Post testPost = Post.builder().build();
        PostDetail testPostDetail = PostDetail.builder()
                .post(testPost)
                .build();
        testPost.setPostDetail(testPostDetail);
        testRepository.save(testPostDetail);

        // when
        PostDetail foundPostDetail = testRepository.findByPost(testPost).orElse(null);

        // then
        assertThat(foundPostDetail).isNotNull();
    }

    @Test
    void FindByBlogPost_UnsavedBlogPost_ExceptionThrown() {
        // given
        Post testPost = Post.builder().build();
        PostDetail testPostDetail = PostDetail.builder()
                .post(testPost)
                .build();
        testPost.setPostDetail(testPostDetail);
        mockPostRepository.save(testPost);

        // when
        // then
        assertThatThrownBy(() -> testRepository.findByPost(testPost)
                .orElseThrow(() -> new PostDetailNotFoundException(testPost)))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

}
