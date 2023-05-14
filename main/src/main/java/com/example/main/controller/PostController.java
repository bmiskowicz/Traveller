package com.example.main.controller;

import com.example.main.DTO.request.PostRequest;
import com.example.main.DTO.response.PostResponse;
import com.example.main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("")
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id){
        return postService.getPost(id);
    }


    @PatchMapping(path = "/{id}/update")
    public ResponseEntity<?> updatePost(PostRequest postRequest){
        postService.updatePost(postRequest);
        return ResponseEntity.ok(postRequest.getPostId());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(PostRequest postRequest, HttpServletRequest httpRequest){
        System.out.println(postRequest.getPostId());
        System.out.println(postRequest.getName());
        postService.createPost(postRequest, httpRequest);
        return ResponseEntity.ok(postRequest.getPostId());
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return (ResponseEntity.ok()).build();
    }

}