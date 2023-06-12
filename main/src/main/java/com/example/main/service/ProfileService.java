package com.example.main.service;

import com.example.main.DTO.request.ProfileRequest;
import com.example.main.DTO.response.ProfileResponse;
import com.example.main.config.security.JWTUtils;
import com.example.main.entity.log.Login;
import com.example.main.entity.Profile;
import com.example.main.repository.ProfileRepository;
import com.example.main.repository.log.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    private JWTUtils jwtUtils;

    public List<ProfileResponse> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(ProfileResponse::new)
                .collect(Collectors.toList());
    }

    public ProfileResponse getProfile(Long id){
        Profile profile = null;
        if(profileRepository.existsById(id)) {
            profile = profileRepository.findById(id).get();
        }
        return new ProfileResponse(profile);

    }

    public ProfileResponse createProfile(Login login){
        Profile profile = Profile.builder()
                .profileId(login.getLoginId())
                .login(login)
                .build();
        profileRepository.save(profile);
        return new ProfileResponse(profile);
    }

    public ResponseEntity<?> updateProfile(ProfileRequest profileRequest, HttpServletRequest httpRequest){

        if(profileRepository.existsById(profileRequest.getProfileId())) {

            String token = httpRequest.getHeader("Authorization");
            String username = jwtUtils.getUserNameFromJwtToken(token);
            Login login = loginRepository.findByUsername(username).get();

            if(!login.getLoginId().equals(profileRequest.getProfileId()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();



            Profile profile = profileRepository.findById(profileRequest.getProfileId()).get();
            Login loginToChange = profile.getLogin();
            loginToChange.setUsername(profileRequest.getUsername());
            loginToChange.setEmail(profileRequest.getEmail());
            loginToChange.setPassword(profileRequest.getPassword());
            loginRepository.save(loginToChange);
            profile.setIcon(profileRequest.getIcon());
            profileRepository.save(profile);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> deleteProfile(Long id, HttpServletRequest httpRequest){
        if(profileRepository.existsById(id)) {
            String token = httpRequest.getHeader("Authorization");
            String username = jwtUtils.getUserNameFromJwtToken(token);
            Login login = loginRepository.findByUsername(username).get();

            if(!login.getLoginId().equals(id))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            Profile profileToDelete = profileRepository.findById(id).get();
            Login loginToDelete = profileToDelete.getLogin();
            profileRepository.delete(profileToDelete);
            loginRepository.delete(loginToDelete);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
