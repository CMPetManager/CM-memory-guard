package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlbumController implements AlbumControllerApiDelegate {

    private final AlbumService albumService;

    public ResponseEntity<AlbumModel> getAlbum(@PathVariable Long albumId) {
        return ResponseEntity.ok(albumService.getByAlbum(albumId));
    }




    public ResponseEntity<Object> deleteAlbum(@PathVariable Long albumId) throws ServiceProcessingException {
        albumService.deleteAlbumById(albumId);
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<List<AlbumModel>> getUserAlbums(@PathVariable Long userId) throws ServiceProcessingException {
        return ResponseEntity.ok().body((List<AlbumModel>) albumService.findAllAlbumsUser(userId));
    }


    public ResponseEntity<AlbumModel> createAlbum(@RequestBody @ModelAttribute AlbumModel model) throws ServiceProcessingException {
        return ResponseEntity.ok().body(albumService.createAlbum(model));
    }

    public ResponseEntity<AlbumModel> updateAlbum(@PathVariable Long albId){
        return ResponseEntity.ok().body(albumService.updateAlbum(albId));
    }

}
