package com.example.main.repository;

import com.example.main.entity.Image;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {

    List<Image> findAll();

    List<Image> findAll(Sort sort);

    Optional<Image> findByImageId(Long aLong);


    boolean existsByImageId(Long aLong);

    void deleteByImageId(Long aLong);

}
