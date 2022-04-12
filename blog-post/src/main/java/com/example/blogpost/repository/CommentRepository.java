package com.example.blogpost.repository;

import com.example.blogpost.entity.Comment;
import com.example.blogpost.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c where c.post = ?1")
    List<Comment> findAllByPost(Post requestedPost);

}
