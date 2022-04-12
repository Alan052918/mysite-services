package com.example.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq")
    private Long id;

    private String title;

    private Long userIdCreated;
    private Long userIdLastModified;

    private ZonedDateTime dateTimeCreated;
    private ZonedDateTime dateTimeLastModified;

    private Long commentCount;
    private Long likeCount;
    private Long shareCount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private PostDetail postDetail;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Tag> tags;

}
