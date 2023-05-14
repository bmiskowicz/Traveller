package com.example.main.DTO.request;


import com.example.main.entity.Image;
import com.example.main.entity.Profile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class PostRequest {


    @NotNull
    private Long postId;

    private String name;

    private String content;

    private Set<ImageRequest> imagesToUpload = new HashSet<>();
}
