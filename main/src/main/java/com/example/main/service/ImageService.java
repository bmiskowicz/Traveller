package com.example.main.service;

import com.example.main.DTO.request.ImageRequest;
import com.example.main.DTO.response.ImageResponse;
import com.example.main.entity.Image;
import com.example.main.entity.Post;
import com.example.main.repository.ImageRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import java.util.*;
import java.util.stream.Stream;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;


    @SneakyThrows
    @Transactional
    public Image saveImage(ImageRequest imageRequest, Post post){

        Image image = new Image(imageRequest.getData(), imageRequest.getFormat(), imageRequest.getName(), post);
        return imageRepository.save(image);
    }

    public ImageResponse getImage(Long id){
        Image image = null;
        if(imageRepository.existsByImageId(id)) {
            image = imageRepository.findByImageId(id).get();
        }
        return new ImageResponse(Base64.getDecoder().decode(image.getData()),
                Objects.equals(image.getFormat(), "jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG);
    }

    public Stream<Image> getAllImages(){
        return imageRepository.findAll().stream();
    }

    public List<Image> updateAllImages(List<Image> actualImages, List<ImageRequest> imageRequests, Post post){
        List<Image> images = new ArrayList<>();


        for (ImageRequest imageRequest: imageRequests) {
            images.add(saveImage(imageRequest, post));
            if(!imageRepository.existsByImageId(imageRequest.getImageId())) saveImage(imageRequest, post);
        }


        for (Image image: actualImages) {
            if(!images.contains(image)) imageRepository.deleteByImageId(image.getImageId());
        }

        return images;
    }

    public void deleteImage(Long id){
        Image image = null;
        if(imageRepository.existsByImageId(id)) {
            image = imageRepository.findByImageId(id).get();
        }
        imageRepository.delete(image);
    }

    @SneakyThrows
    public List<Image> saveAllImages(List<ImageRequest> imageRequests, Post post) {
        List<Image> images = new ArrayList<>();
        for (ImageRequest imageRequest: imageRequests) {
            images.add(saveImage(imageRequest, post));
        }
        return images;
    }

    public void deleteAllImages(Post post) {
        imageRepository.deleteAllByPost(post);
    }
}
