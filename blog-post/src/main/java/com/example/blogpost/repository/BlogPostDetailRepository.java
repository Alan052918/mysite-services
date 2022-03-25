package com.example.blogpost.repository;

import com.example.blogpost.entity.BlogPostDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostDetailRepository extends JpaRepository<BlogPostDetail, Long> {

}
