package com.example.blog_post.repository;

import com.example.blog_post.entity.BlogPostDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostDetailRepository extends JpaRepository<BlogPostDetail, Long> {

}
