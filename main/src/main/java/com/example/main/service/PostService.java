package com.example.main.service;

import com.example.main.DTO.request.PostRequest;
import com.example.main.DTO.response.PostResponse;
import com.example.main.entity.Post;
import com.example.main.entity.Profile;
import com.example.main.entity.log.Login;
import com.example.main.repository.PostRepository;
import com.example.main.repository.ProfileRepository;
import com.example.main.repository.log.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.main.config.security.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageService imageService;
    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    ProfileRepository profileRepository;


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


    public void deletePost(Long id) {
        if (postRepository.existsByPostId(id)) {
            postRepository.deleteByPostId(id);
        }
    }

    public PostResponse updatePost(PostRequest postRequest) {
        Post post = null;
        if(postRepository.existsByPostId(postRequest.getPostId())){
            post = postRepository.findByPostId(postRequest.getPostId()).get();
            post.setContent(postRequest.getContent());
            post.setName(postRequest.getName());
            post.setImagesToUpload(imageService.updateAllImages(post.getImagesToUpload(), postRequest.getImagesToUpload()));
        }
        return new PostResponse(post);
    }

    public PostResponse createPost(PostRequest postRequest, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Login login = loginRepository.findByUsername(username).get();
        Profile profile = profileRepository.findById(login.getLoginId()).get();

        Post post = Post.builder()
                .content(postRequest.getContent())
                .name(postRequest.getName())
                .profile(profile)
                .imagesToUpload(imageService.saveAllImages(postRequest.getImagesToUpload()))
                .build();
        postRepository.save(post);
        return new PostResponse(post);
    }
}
