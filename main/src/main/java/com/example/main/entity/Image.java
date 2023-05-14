package com.example.main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "profiles", name="image")
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "imageId")
    private Long imageId;

    @Column(nullable = false)
    private String src;

    @Column(nullable = false)
    private String alt;


    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="postId", nullable = false)
    private Post post;

    public Image(String src, String alt) {
        this.src = src;
        this.alt = alt;
    }
}
