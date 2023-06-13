package com.example.main.DTO.response;

import com.example.main.entity.Image;
import com.example.main.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PostResponse {
    public Long postId;

    public String name;

    public String content;

    public List<PostImageResponse> images = new ArrayList<>();


    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.name = post.getName();
        this.content = post.getContent();
        List<Image> imageList = post.getImagesToUpload();
        for (Image i: imageList) {
            this.images.add(new PostImageResponse("https://localhost:442/images/" + i.getImageId(), i.getName()));
        }
    }
}
