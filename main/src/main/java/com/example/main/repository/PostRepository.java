package com.example.main.repository;

import com.example.main.entity.Image;
import com.example.main.entity.Post;
import com.example.main.entity.log.Login;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,String> {

    List<Post> findAll();

    List<Post> findAll(Sort sort);

    List<Post> findAllByPostId(Long aLong);

    Optional<Post> findByPostId(Long aLong);

    boolean existsByPostId(Long aLong);

    void deleteByPostId(Long aLong);
}
