package com.example.main.DTO.response;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

@Setter
@Getter
public class ImageResponse {
    private byte[] src;

    private MediaType type;

    public ImageResponse(byte[] src, MediaType type) {
        this.src = src;
        this.type = type;
    }
}
