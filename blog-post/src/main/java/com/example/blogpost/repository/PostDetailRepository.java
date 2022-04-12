package com.example.blogpost.repository;

import com.example.blogpost.entity.Post;
import com.example.blogpost.entity.PostDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostDetailRepository extends JpaRepository<PostDetail, Long> {

    @Query(value = "select bpd from PostDetail bpd where bpd.post = ?1")
    Optional<PostDetail> findByPost(Post post);

}
