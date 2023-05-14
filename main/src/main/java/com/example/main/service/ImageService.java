package com.example.main.service;

import com.example.main.DTO.request.ImageRequest;
import com.example.main.entity.Image;
import com.example.main.repository.ImageRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;


    @SneakyThrows
    @Transactional
    public Image saveImage(ImageRequest imageRequest){
        Image image = new Image(Base64.getEncoder().encodeToString(imageRequest.getData().getBytes()), imageRequest.getFormat());
        return imageRepository.save(image);
    }

    public Image getImage(String id){
        Image image = null;
        if(imageRepository.existsById(id)) {
            image = imageRepository.findById(id).get();
        }
        return image;
    }

    public Stream<Image> getAllImages(){
        return imageRepository.findAll().stream();
    }

    @SneakyThrows
    @Transactional
    public void updateImage(ImageRequest imageRequest) {
        Image image = null;
        if(imageRepository.existsByImageId(imageRequest.getImageId())){
            image = imageRepository.findByImageId(imageRequest.getImageId()).get();
            image.setSrc(Base64.getEncoder().encodeToString(imageRequest.getData().getBytes()));
            image.setAlt(imageRequest.getName());
        }
        imageRepository.save(image);
    }
    
    public Set<Image> updateAllImages(Set<Image> actualImages, Set<ImageRequest> imageRequests){
        Set<Image> images = new HashSet<>();


        for (ImageRequest imageRequest: imageRequests) {
            images.add(saveImage(imageRequest));
            if(imageRepository.existsByImageId(imageRequest.getImageId())){
                updateImage(imageRequest);
            }
            else saveImage(imageRequest);
        }


        for (Image image: actualImages) {
            if(!images.contains(image)) imageRepository.deleteByImageId(image.getImageId());
        }

        return images;
    }

    public void deleteImage(String id){
        Image image = null;
        if(imageRepository.existsById(id)) {
            image = imageRepository.findById(id).get();
        }
        imageRepository.delete(image);
    }

    @SneakyThrows
    public Set<Image> saveAllImages(Set<ImageRequest> imageRequests) {
        Set<Image> images = new HashSet<>();
        for (ImageRequest imageRequest: imageRequests) {
            images.add(saveImage(imageRequest));
        }
        return images;
    }

}
