package com.catchthemoment.service;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.mappers.AlbumMapperImpl;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.repository.UserRepository;
import com.catchthemoment.validation.ReadDataTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.catchthemoment.exception.ApplicationErrorEnum.ALBUM_ERROR_INPUT;

@Service
@Slf4j
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final AlbumMapper albumMapper;

    public AlbumService(AlbumRepository albumRepository, UserRepository userRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.albumMapper = new AlbumMapperImpl();
    }

    @ReadDataTransactional
    public AlbumModel getByAlbum(@NotNull Long albumId) {
        log.info("get album by id from repo");
        Album currentAlbum = albumRepository.findAlbumById(albumId)
                .orElse(new Album());
        log.info("map model from entity ");
        return albumMapper.fromAlbumEntity(currentAlbum);
    }

    @ReadDataTransactional
    public Iterable<AlbumModel> findAllAlbumsUser(@NotNull Long userId) throws ServiceProcessingException {
        log.info(" get user by incoming id");
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceProcessingException(
                        ALBUM_ERROR_INPUT));
        log.info("map album model list from incoming entity album list");
        return albumMapper.fromAlbumEntities(user.getAlbums());
    }

    public void deleteAlbumById(Long albumId) throws ServiceProcessingException {
        if (albumId == null) {
            log.debug("incoming id was not found");
            throw new ServiceProcessingException(ALBUM_ERROR_INPUT);
        }
        albumRepository.deleteAlbumById(albumId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public AlbumModel updateAlbum(@NotNull Long albumId, AlbumModel albumModel) throws ServiceProcessingException {
        Album requestAlbum = albumMapper.fromAlbumModel(albumModel);
        Album album = albumRepository.findAlbumById(albumId)
                .orElseThrow(() -> new ServiceProcessingException(ALBUM_ERROR_INPUT));
        setDataFromModelToEntity(requestAlbum, album);
        return albumMapper.fromAlbumEntity(albumRepository.save(album));
    }

    private void setDataFromModelToEntity(Album requestAlbum, Album album) {
        album.setCover(requestAlbum.getCover());
        album.setId(requestAlbum.getId());
        album.setAlbumDescription(requestAlbum.getAlbumDescription());
        album.setAlbumName(requestAlbum.getAlbumName());
        album.setColor(requestAlbum.getColor());
        album.setTemplatePage(requestAlbum.getTemplatePage());
        album.setAnimation(requestAlbum.getAnimation());
        album.setTagPeople(requestAlbum.getTagPeople());
        album.setTagPlace(requestAlbum.getTagPlace());


    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AlbumModel createAlbum(AlbumModel model) throws ServiceProcessingException {
        if (model == null) {
            throw new ServiceProcessingException(ALBUM_ERROR_INPUT);
        }
        Album album = albumMapper.fromAlbumModel(model);
        return albumMapper.fromAlbumEntity(albumRepository.save(album));
    }

    @ReadDataTransactional
    public AlbumModel getAlbumByName(@NotNull @NotEmpty  String name)
            throws ServiceProcessingException {
        var currentAlbum = albumRepository.findAlbumByName(name)
                .orElseThrow(() -> new ServiceProcessingException(ALBUM_ERROR_INPUT));
        return albumMapper.fromAlbumEntity(currentAlbum);

    }
}