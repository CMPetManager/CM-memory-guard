package com.catchthemoment.repository;

import com.catchthemoment.entity.Image;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @EntityGraph(value = "image-graph", attributePaths = {"album,user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Image> findImageByName(String fileName);

    @Modifying
    @Query("delete from Image img where img.id = :id")
    @EntityGraph(value = "image-graph",attributePaths = {"album,user"},type = EntityGraph.EntityGraphType.FETCH)
    void deleteImageById(@Param("id") Long id);
}
