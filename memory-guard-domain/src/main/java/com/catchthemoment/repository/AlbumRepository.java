package com.catchthemoment.repository;

import com.catchthemoment.entity.Album;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findAlbumById(Long id);

    @EntityGraph(value = "album_graph", type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"images,user"})
    @Query(value = "select *  from Album al where al.id =id group by" +
            " al.id having al.id is not null ",nativeQuery = true)
    List<Album> findAllByUserId(@Param("id") Long userId);

    @Modifying
    @Query("select al from Album al where al.id = :id ")
    void deleteAlbumById(@Param("id") Long id);

    @Query(value = "select al from Album al where al.albumName =:name ")
    @EntityGraph(value = "album_graph", type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"images,user"})
    Optional<Album> findAlbumByName(@Param("name") String name);



}