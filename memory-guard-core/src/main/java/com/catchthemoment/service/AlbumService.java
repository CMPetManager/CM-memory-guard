package com.catchthemoment.service;

import com.catchthemoment.entity.Album;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.catchthemoment.exception.ApplicationErrorEnum.ALBUM_ERROR_INPUT;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final UserRepository userRepository;

    public AlbumModel getByAlbum(@NotNull Long albumId) {
        log.info("get album by id from repo");
        Album currentAlbum = albumRepository.findAlbumById(albumId)
                .orElse(new Album());
        log.info("map model from entity ");
        return albumMapper.fromAlbumEntity(currentAlbum);
    }
    public AlbumModel getAlbumByName(@NotNull @NotBlank String name) throws ServiceProcessingException {
        return albumMapper.fromAlbumEntity(albumRepository.findAlbumByName(name)
                .orElseThrow(()-> new ServiceProcessingException(ALBUM_ERROR_INPUT.getCode(),
                        ALBUM_ERROR_INPUT.getMessage())));
    }

    public Collection<AlbumModel> findAllAlbumsUser(@NotNull Long userId) throws ServiceProcessingException {
        log.info(" get user by incoming id");
        var user = userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceProcessingException(
                        ALBUM_ERROR_INPUT.getCode(), ALBUM_ERROR_INPUT.getMessage()));
        log.info("map album model list from incoming entity album list");
        return albumMapper.fromAlbumEntities(user.getAlbums()
                .stream().sorted()
                .distinct()
                .toList());
    }

    public void deleteAlbumById(@NotNull Long albumId) throws ServiceProcessingException {
        if (albumId == null) {
            log.debug("incoming id was not found");
            throw new ServiceProcessingException(ALBUM_ERROR_INPUT.getCode(),
                    ALBUM_ERROR_INPUT.getMessage());
        }
        albumRepository.deleteAlbumById(albumId);
    }

    public AlbumModel updateAlbum(@NotNull Long albumId) {
        Album album = albumRepository.findAlbumById(albumId)
                .orElse(new Album());
        album.setCover(album.getCover());
        album.setUser(album.getUser());
        return albumMapper.fromAlbumEntity(albumRepository.save(album));

    }
    public AlbumModel createAlbum(AlbumModel model) throws ServiceProcessingException {
        if (model == null){
            throw new ServiceProcessingException(ALBUM_ERROR_INPUT.getCode(),
                    ALBUM_ERROR_INPUT.getMessage());
        }
        Album album = albumMapper.fromAlbumModel(model);
        return albumMapper.fromAlbumEntity(albumRepository.save(album));
    }
}
