package com.example.main.controller;

import com.example.main.DTO.response.ImageResponse;
import com.example.main.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ImageResponse imageResponse = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(imageResponse.getType())
                .body(imageResponse.getSrc());
    }
}
