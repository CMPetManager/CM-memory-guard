package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlbumController implements AlbumControllerApiDelegate {

    private final AlbumService albumService;

    @Override
    public ResponseEntity<AlbumModel> getAlbum(Long albumId) {
        return ResponseEntity.ok(albumService.getByAlbum(albumId));
    }

    @Override
    public ResponseEntity<Object> deleteAlbum(Long albumId) throws ServiceProcessingException {
        albumService.deleteAlbumById(albumId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<List<AlbumModel>> getUserAlbums(Long userId) throws ServiceProcessingException {
        return ResponseEntity.ok().body((List<AlbumModel>) albumService.findAllAlbumsUser(userId));
    }

    @Override
    public ResponseEntity<AlbumModel> createAlbum(@ModelAttribute AlbumModel model) throws ServiceProcessingException {
        return ResponseEntity.ok().body(albumService.createAlbum(model));
    }

    @Override
    public ResponseEntity<AlbumModel> updateAlbum(Long albumId, AlbumModel albumModel) throws Exception {
        return ResponseEntity.ok().body(albumService.updateAlbum(albumId,albumModel));
    }

    @Override
    public ResponseEntity<AlbumModel> getAlbumByName(String name) throws Exception {
        return ResponseEntity.ok().body(albumService.getAlbumByName(name));
    }

}
