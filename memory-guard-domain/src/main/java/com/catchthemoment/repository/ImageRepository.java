package com.catchthemoment.repository;

import com.catchthemoment.entity.Image;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @EntityGraph(value = "image-graph", attributePaths = {"album"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Image> findImageByName(String fileName);

    void deleteImageById(Long id);
}
