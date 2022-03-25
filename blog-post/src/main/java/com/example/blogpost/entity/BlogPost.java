package com.example.blogpost.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_post_id_generator")
    @SequenceGenerator(name = "blog_post_id_generator", sequenceName = "blog_post_id_sequence")
    private Long id;

    private String title;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BlogPost blogPost = (BlogPost) o;
        return id != null && Objects.equals(id, blogPost.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
