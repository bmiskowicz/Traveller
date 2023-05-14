package com.example.main.repository.log;

import com.example.main.entity.log.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository  extends JpaRepository<Login, Long> {
    Optional<Login> findByUsername(String username);

    @Override
    Optional<Login> findById(Long aLong);

    Login findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}