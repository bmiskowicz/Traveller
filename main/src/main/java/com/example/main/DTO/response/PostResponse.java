package com.example.main.DTO.response;

import com.example.main.entity.Image;
import com.example.main.entity.Post;

import java.util.Set;

public class PostResponse {
    public Long postId;

    public String name;

    public String content;

    public Set<Image> images;


    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.name = post.getName();
        this.content = post.getContent();
        this.images = post.getImagesToUpload();
    }
}
