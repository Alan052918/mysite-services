package com.example.blogpost.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class BlogPostDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_post_detail_id_generator")
    @SequenceGenerator(name = "blog_post_detail_id_generator", sequenceName = "blog_post_detail_id_sequence")
    private Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_post_detail_sequence_generator")
    @SequenceGenerator(name = "blog_post_detail_sequence_generator", sequenceName = "blog_post_detail_sequence")
    private Long sequence;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToOne(mappedBy = "blogPostDetail")
    private BlogPost blogPost;

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BlogPostDetail that = (BlogPostDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
