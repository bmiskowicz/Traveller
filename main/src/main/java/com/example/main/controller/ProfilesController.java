package com.example.main.controller;
import com.example.main.DTO.request.ProfileRequest;
import com.example.main.DTO.response.ProfileResponse;
import com.example.main.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfilesController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("")
    public List<ProfileResponse> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ProfileResponse getProfile(@PathVariable Long id){
        return profileService.getProfile(id);
    }


    @PatchMapping(path = "/{id}/update")
    public ResponseEntity<?> updateProfile(ProfileRequest profileRequest){
        ProfileResponse profileResponse = profileService.updateProfile(profileRequest);
        return ResponseEntity.ok(profileResponse);
    }


    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id){
        profileService.deleteProfile(id);
        return (ResponseEntity.ok()).build();
    }
}
