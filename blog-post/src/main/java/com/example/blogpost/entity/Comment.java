package com.example.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeLastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Post post;

    private Long replyToId;

    @ElementCollection
    private List<Long> replyIds;

}
