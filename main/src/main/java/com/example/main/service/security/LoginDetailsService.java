package com.example.main.service.security;

import com.example.main.config.security.UserDetailsImplementation;
import com.example.main.entity.log.Login;
import com.example.main.repository.log.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class LoginDetailsService implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepository.findByUsername(username)
                .orElseThrow(() -> new ExpressionException("User Not Found"));
        return UserDetailsImplementation.build(login);
    }


}