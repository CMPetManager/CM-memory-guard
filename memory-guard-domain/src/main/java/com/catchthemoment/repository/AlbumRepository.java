package com.catchthemoment.repository;

import com.catchthemoment.entity.Album;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @EntityGraph(value = "album-graph", type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"user"})
    Optional<Album> findAlbumById(Long id);

    @EntityGraph(value = "album_graph", type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"images,user"})
    @Query(value = "select *  from Album al where al.id =id group by" +
            " al.id having al.id is not null ",nativeQuery = true)
    List<Album> findAllByUserId(@Param("id") Long userId);

    @Modifying
    @Query("delete from Album al where al.id =:id")
    void deleteAlbumById(@Param("id") Long id);

    @Query(value = "select al from Album al where al.albumName =:name ")
    @EntityGraph(value = "album-graph", type = EntityGraph.EntityGraphType.LOAD ,attributePaths = {"user"})
    Optional<Album> findAlbumByName(@Param("name") String name);



}