package com.catchthemoment.service;

import com.catchthemoment.entity.Image;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.catchthemoment.exception.ApplicationErrorEnum.ILLEGAL_STATE;
import static com.catchthemoment.exception.ApplicationErrorEnum.IMAGE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private static final String FOLDER_PATH = "C:\\Users\\Admin\\gitlab\\";

    private final ImageRepository imageRepository;

    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException, ServiceProcessingException {
        log.info("Checking the image name for uniqueness");
        if (imageRepository.findImageByName(file.getOriginalFilename()).isPresent()) {
            log.error("*** This image is already exists ***");
            throw new ServiceProcessingException(ILLEGAL_STATE.getCode(),ILLEGAL_STATE.getMessage());
        }
        log.info("Validation passed, This name doesn't exist in the database");
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Image buildImage = getBuildImage(file, filePath);
        log.info("Save object image into db");

        Image image = imageRepository.save(buildImage);
        log.info("Image successful saved");
        file.transferTo(new File(filePath));

        return image;
    }

    public byte[] downloadImage(String fileName) throws ServiceProcessingException, IOException {
        log.info("Find image name in the db");
        Image currentImage = imageRepository.findImageByName(fileName)
                .orElseThrow(() -> new ServiceProcessingException(
                        IMAGE_NOT_FOUND.getCode(),
                        IMAGE_NOT_FOUND.getMessage()));
        log.info("Name successfully found");
        String filePath = currentImage.getLink();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    private static Image getBuildImage(MultipartFile file, String filePath) {
        return Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .link(filePath)
                .build();
    }
}
