package com.example.main.controller.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.main.DTO.request.log.AuthRequest;
import com.example.main.DTO.request.log.LoginRequest;
import com.example.main.DTO.response.log.LoginResponse;
import com.example.main.config.security.JWTUtils;
import com.example.main.config.security.UserDetailsImplementation;
import com.example.main.entity.log.VerificationToken;
import com.example.main.repository.log.LoginRepository;
import com.example.main.repository.log.VerificationTokenRepository;
import com.example.main.service.security.AuthService;
import com.example.main.service.security.LoginService;
import com.example.main.service.security.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    LoginService loginService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTUtils jwtUtils;

    @GetMapping("/registrationConfirm/{token}")
    public ResponseEntity<?> confirmUser(@PathVariable String token){
        try {
            Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
            String email = verificationToken.get().getUnverifiedUserEmail();
            verificationTokenRepository.deleteByToken(token);
            loginService.verifyLogin(email);

            return ResponseEntity.ok().body("Verified successfully");
        }
        catch (Exception e){
            return ResponseEntity.ok().body("Verification code not found");
        }
    }
    {

    }
    @Autowired
    AuthService authService;
    @PostMapping("api/auth/signin")
    public ResponseEntity<?> getToken(@Valid @RequestBody AuthRequest loginRequest, HttpServletRequest httpServletRequest){
        System.out.println(httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok(authService.getAuthToken(loginRequest));
    }

    @PostMapping("api/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest loginRequest) {
        if (loginRepository.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Username is already taken!");
        }

        if (loginRepository.existsByEmail(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Email is already in use!");
        }

        LoginResponse loginResponse = loginService.createLogin(loginRequest);
        verificationTokenService.sendVerificationToken(loginRequest.getEmail());


        return (ResponseEntity<String>) ResponseEntity.ok("User registered successfully with " + loginResponse.getUsername());
    }

    @PostMapping("api/auth/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ResponseEntity.ok("You've been signed out!"));
    }
}