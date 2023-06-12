package com.example.main.service;

import com.example.main.DTO.request.PostRequest;
import com.example.main.DTO.response.PostResponse;
import com.example.main.entity.Image;
import com.example.main.entity.Post;
import com.example.main.entity.Profile;
import com.example.main.entity.log.Login;
import com.example.main.repository.ImageRepository;
import com.example.main.repository.PostRepository;
import com.example.main.repository.ProfileRepository;
import com.example.main.repository.log.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.main.config.security.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageService imageService;
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ProfileRepository profileRepository;


    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public PostResponse getPost(Long id) {
        Post post = null;
        if (postRepository.existsByPostId(id)) {
            post = postRepository.findByPostId(id).get();
        }
        return new PostResponse(post);
    }


    public ResponseEntity<?> deletePost(Long id, HttpServletRequest httpRequest) {
        if (postRepository.existsByPostId(id)) {
            Post post = postRepository.findByPostId(id).get();

            String token = httpRequest.getHeader("Authorization");
            String username = jwtUtils.getUserNameFromJwtToken(token);
            Login login = loginRepository.findByUsername(username).get();

            if(post.getProfile().getProfileId().equals(profileRepository.findById(login.getLoginId()).get().getProfileId())){
                imageService.deleteAllImages(post);
                postRepository.deleteByPostId(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> updatePost(Long id, PostRequest postRequest, HttpServletRequest httpRequest) {
        if(Objects.equals(id, postRequest.getPostId()) && postRepository.existsByPostId(postRequest.getPostId())){
            Post post = postRepository.findByPostId(postRequest.getPostId()).get();

            if(postRepository.existsByName(postRequest.getName()))
                if(!postRepository.findByName(postRequest.getName()).get().getPostId().equals(id))
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Post with such name already exists");

            String token = httpRequest.getHeader("Authorization");
            String username = jwtUtils.getUserNameFromJwtToken(token);
            Login login = loginRepository.findByUsername(username).get();


            if(post.getProfile().getProfileId().equals(profileRepository.findById(login.getLoginId()).get().getProfileId())) {
                post.setImagesToUpload(imageService.updateAllImages(post.getImagesToUpload(), postRequest.getImagesToUpload(), post));
                post.setContent(postRequest.getContent());
                post.setName(postRequest.getName());
                postRepository.save(post);
                return ResponseEntity.status(HttpStatus.OK).body(post.getPostId());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> createPost(PostRequest postRequest, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        String username = jwtUtils.getUserNameFromJwtToken(token);
        if (loginRepository.existsByUsername(username)) {

            if(postRepository.existsByName(postRequest.getName()))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Post with such name already exists");

            Login login = loginRepository.findByUsername(username).get();
            Profile profile = profileRepository.findById(login.getLoginId()).get();

            Post post = Post.builder()
                    .content(postRequest.getContent())
                    .name(postRequest.getName())
                    .profile(profile)
                    .build();

            List<Image> imagesToUpload = imageService.saveAllImages(postRequest.getImagesToUpload(), post);
            post.setImagesToUpload(imagesToUpload);

            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(post.getPostId());
        }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
