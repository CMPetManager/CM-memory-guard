package com.catchthemoment.repository;

import com.catchthemoment.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findAlbumById(Long albumId);
}
