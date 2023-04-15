package com.catchthemoment.controller;

import com.catchthemoment.mappers.ImageMapper;
import com.catchthemoment.model.Image;
import com.catchthemoment.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageController implements ImageControllerApiDelegate {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    @Override
    public ResponseEntity<Image> uploadImage(MultipartFile file) throws Exception {
        log.info("Received an upload image request with file name: {}",file.getOriginalFilename());
        com.catchthemoment.entity.Image uploadedImage = imageService.uploadImage(file);
        log.info("Upload was successful");
        Image currentImage = imageMapper.toDto(uploadedImage);
        return ResponseEntity.ok(currentImage);
    }

    @Override
    public ResponseEntity<byte[]> downloadImage(String name) throws Exception {
        log.info("Received an download image request by name: {}", name);
        byte[] imageData = imageService.downloadImage(name);
        log.info("Upload was successful");
        return ResponseEntity.ok(imageData);
    }

}
