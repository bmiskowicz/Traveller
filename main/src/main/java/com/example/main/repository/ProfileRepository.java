package com.example.main.repository;

import com.example.main.entity.Profile;
import com.example.main.entity.log.Login;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findAll();

    List<Profile> findAll(Sort sort);

    List<Profile> findAllById(Iterable<Long> longs);

    Optional<Profile> findById(Long aLong);


    boolean existsById(Long aLong);

    void deleteById(Long aLong);

    void deleteAllById(Iterable<? extends Long> longs);
}