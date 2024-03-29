package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin
public class AlbumController implements AlbumControllerApiDelegate {

    private final AlbumService albumService;

    @Override
    @PreAuthorize("@loginHandler.canAccessToAlbumActions()")
    public ResponseEntity<AlbumModel> getAlbum(Long albumId) {
        return ResponseEntity.ok(albumService.getByAlbum(albumId));
    }

    @Override
    public ResponseEntity<Object> deleteAlbum(Long albumId) throws ServiceProcessingException {
        albumService.deleteAlbumById(albumId);
        return ResponseEntity.accepted().build();
    }

    @Override
    @PreAuthorize("@loginHandler.canAccessToAlbumActions()")
    public ResponseEntity<List<AlbumModel>> getUserAlbums(Long userId) throws ServiceProcessingException {
        return ResponseEntity.ok().body((List<AlbumModel>) albumService.findAllAlbumsUser(userId));
    }

    @Override
    @PreAuthorize("@loginHandler.canAccessToAlbumActions()")
    public ResponseEntity<AlbumModel> createAlbum(@ModelAttribute AlbumModel model) throws ServiceProcessingException {
        return ResponseEntity.ok().body(albumService.createAlbum(model));
    }

    @Override
    @PreAuthorize("@loginHandler.canAccessToAlbumActions()")
    public ResponseEntity<AlbumModel> updateAlbum(Long albumId, AlbumModel albumModel) throws Exception {
        albumService.updateAlbum(albumId,albumModel);
        return (ResponseEntity<AlbumModel>) ResponseEntity.ok();
    }

    @Override
    @PreAuthorize("@loginHandler.canAccessToAlbumActions()")
    public ResponseEntity<AlbumModel> getAlbumByName(String name) throws Exception {
        log.info("1");
        return ResponseEntity.ok().body(albumService.getAlbumByName(name));
    }

}