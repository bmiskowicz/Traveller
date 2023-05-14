package com.example.main.DTO.response;


import com.example.main.entity.Image;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageResponse {
    private String src;
    private String alt;

    public ImageResponse(String src, String alt) {
        this.src = src;
        this.alt = alt;
    }
}
