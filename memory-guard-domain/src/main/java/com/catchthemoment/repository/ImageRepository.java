package com.catchthemoment.repository;

import com.catchthemoment.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findImageByName(String fileName);

    void deleteImageById(Long id);

}
