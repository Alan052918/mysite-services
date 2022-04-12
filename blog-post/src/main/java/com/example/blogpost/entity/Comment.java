package com.example.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long userIdCreated;

    private ZonedDateTime dateTimeCreated;
    private ZonedDateTime dateTimeLastModified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
//    @JsonIgnore
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reply_to_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"post"})
//    @JsonIgnore
    private Comment replyTo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "replyTo")
    @JsonIgnore
    private List<Comment> replies;

}
