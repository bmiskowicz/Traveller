package com.example.main.service.security;

import com.example.main.DTO.request.log.LoginRequest;
import com.example.main.DTO.response.ProfileResponse;
import com.example.main.DTO.response.log.LoginResponse;
import com.example.main.entity.log.Login;
import com.example.main.repository.log.LoginRepository;
import com.example.main.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private ProfileService profileService;
    @Autowired
    LoginRepository loginRepository;
    public LoginResponse createLogin(LoginRequest loginRequest){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Login login = new Login();
        login.setUsername(loginRequest.getUsername());
        login.setEmail(loginRequest.getEmail());
        login.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        login.setRoles(loginRequest.getRoles());
        login.setEnabled(false);
        loginRepository.save(login);

        return new LoginResponse(login);
    }

    public LoginResponse verifyLogin(String email){
        Login login = null;
        if(loginRepository.existsByEmail(email)) {
            login = loginRepository.findByEmail(email);
            login.setEnabled(true);
            loginRepository.save(login);

            profileService.createProfile(login);
        }
        return new LoginResponse(login);

    }

}
