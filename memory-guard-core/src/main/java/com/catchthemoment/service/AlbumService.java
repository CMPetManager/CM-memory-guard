package com.catchthemoment.service;

import com.catchthemoment.entity.Album;
import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final UserRepository userRepository;

    public AlbumModel getByAlbumId(@NotNull Long albumId) {
        log.info("get album by id from repo");
        Album currentAlbum = albumRepository.findAlbumById(albumId)
                .orElse(new Album());
        log.info("map model from entity ", currentAlbum);
        AlbumModel albumModel = albumMapper.fromAlbumEntity(currentAlbum);
        return albumModel;
    }

    public Collection<AlbumModel> findAllAlbumsUser(@NotNull Long userId) throws ServiceProcessingException {
        log.info(" get user by incoming id");
        var user =  userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceProcessingException(
                        ApplicationErrorEnum.ALBUM_ERROR_INPUT.getCode(), ApplicationErrorEnum.ALBUM_ERROR_INPUT.getMessage()));
        log.info("map album model list from incoming entity album list");
        var albumModelList = albumMapper.fromAlbumEntities(user.getAlbums()
                .stream().sorted()
                .filter(album -> album.getId().longValue() > 0 && album.getUser() != null)
                .toList());
        return albumModelList;

    }

    public void deleteAlbumById(@NotNull Long albumId) throws ServiceProcessingException {
        if (albumId == null) {
            log.debug("incoming id was not found", albumId);
            throw new ServiceProcessingException(ApplicationErrorEnum.ALBUM_ERROR_INPUT.getCode(),
                    ApplicationErrorEnum.ALBUM_ERROR_INPUT.getMessage());
        }
        albumRepository.deleteAlbumById(albumId);
    }

    public AlbumModel updateAlbum(@NotNull Long albumId) {
        Album album = albumRepository.findAlbumById(albumId)
                .orElse(new Album());
        album.setCover(album.getCover());
        album.setPages(album.getPages());
        album.setUser(album.getUser());
        return albumMapper.fromAlbumEntity(albumRepository.save(album));


    }
}
