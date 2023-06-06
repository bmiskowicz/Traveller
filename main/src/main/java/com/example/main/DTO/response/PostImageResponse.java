package com.example.main.DTO.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostImageResponse {
    private String src;

    private String alt;

    public PostImageResponse(String src, String alt) {
        this.src = src;
        this.alt = alt;
    }
}
