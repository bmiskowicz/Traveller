package com.example.main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "profiles", name="post")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "postId")
    private Long postId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @JsonBackReference
    @OneToMany()
    @JoinColumn(name="imageId")
    private Set<Image> imagesToUpload = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name="profileId", nullable = false)
    private Profile profile;
}
