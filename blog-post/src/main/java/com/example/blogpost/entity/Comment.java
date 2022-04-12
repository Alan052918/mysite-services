package com.example.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long userIdLastModified;

    private ZonedDateTime dateTimeCreated;
    private ZonedDateTime dateTimeLastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Post post;

    private Long replyToId;

    @ElementCollection
    @JsonIgnore
    private List<Long> replyIds;

}
