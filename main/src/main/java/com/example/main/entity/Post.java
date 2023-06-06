package com.example.main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Image> imagesToUpload = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name="profileId", nullable = false)
    private Profile profile;
}
