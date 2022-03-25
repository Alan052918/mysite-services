package com.example.blogpost.repository;

import com.example.blogpost.entity.BlogPost;
import com.example.blogpost.entity.BlogPostDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostDetailRepository extends JpaRepository<BlogPostDetail, Long> {

    @Query("select bpd from BlogPostDetail bpd where bpd.blogPost = ?1")
    Optional<BlogPostDetail> findByBlogPost(BlogPost blogPost);

}
