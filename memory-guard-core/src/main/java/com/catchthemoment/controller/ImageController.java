package com.catchthemoment.controller;

import com.catchthemoment.entity.Image;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.ImageMapper;
import com.catchthemoment.model.ImageModel;
import com.catchthemoment.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.catchthemoment.exception.ApplicationErrorEnum.EMPTY_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageController implements ImageControllerApiDelegate {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    @Override
    public ResponseEntity<ImageModel> uploadImage(MultipartFile file) throws Exception {
        log.info("Received an upload image request with file name: {}", file.getOriginalFilename());
        if (!file.isEmpty()) {
            Image uploadedImage = imageService.uploadImage(file);
            log.info("Upload was successful");
            ImageModel currentImage = imageMapper.toModel(uploadedImage);
            return ResponseEntity.ok(currentImage);
        } else {
            throw new ServiceProcessingException(
                    EMPTY_REQUEST.getCode(),
                    EMPTY_REQUEST.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<byte[]>> downloadImage(String name) throws Exception {
        log.info("Received an download image request by name: {}", name);
        byte[] imageData = imageService.downloadImage(name);
        List<byte[]> bytes = List.of(imageData);
        log.info("Upload was successful");
        return ResponseEntity.ok(bytes);
    }
}
