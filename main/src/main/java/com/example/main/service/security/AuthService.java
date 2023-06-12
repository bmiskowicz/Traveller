package com.example.main.service.security;

import com.example.main.DTO.request.log.AuthRequest;
import com.example.main.DTO.response.log.AuthResponse;
import com.example.main.config.security.JWTUtils;
import com.example.main.config.security.UserDetailsImplementation;
import com.example.main.repository.log.LoginRepository;
import com.example.main.repository.log.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtils jwtUtils;

    public AuthResponse getAuthToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateTokenFromUsername(authentication.getName());
        UserDetailsImplementation userDetailsImpl = (UserDetailsImplementation) authentication.getPrincipal();
        List<String> roles = userDetailsImpl.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new AuthResponse(userDetailsImpl.getId(), jwt, userDetailsImpl.getUsername(), userDetailsImpl.getEmail(), roles);
    }

}