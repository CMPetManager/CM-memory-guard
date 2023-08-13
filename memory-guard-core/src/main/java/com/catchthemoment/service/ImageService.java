package com.catchthemoment.service;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.Image;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.model.ImageDescriptionModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.repository.ImageRepository;
import com.catchthemoment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.catchthemoment.exception.ApplicationErrorEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ImageService {
   // private static final String FOLDER_PATH = "C:\\Users\\Admin\\gitlab\\";


     private static final String FOLDER_PATH = "/home/shele/Pictures";
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Image uploadImage(AlbumModel albumModel, MultipartFile file) throws IOException, ServiceProcessingException {
        log.info("*** Checking the image name for uniqueness ***");
        if (imageRepository.findImageByName(file.getOriginalFilename()).isPresent()) {
            log.error("*** This image is already exists ***");
            throw new ServiceProcessingException(ILLEGAL_STATE);
        }
        log.info("*** Validation passed, the imageName doesn't exist in the database ***");
        Path filePath = getPath(file);
        Image buildImage = getBuildImage(file, filePath);
        Album currentAlbum = albumMapper.fromAlbumModel(albumModel);
        buildImage.setAlbum(currentAlbum);
        log.info("*** Save object image into db ***");
        Image image = imageRepository.save(buildImage);
        currentAlbum.setImages(List.of(image));
        albumRepository.save(currentAlbum);
        log.info("*** Image successful saved ***");
        return image;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Image uploadImage(Long userId, MultipartFile file) throws IOException, ServiceProcessingException {
        Path filePath = getPath(file);
        Image buildImage = getBuildImage(file, filePath);
        User currentUser = userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceProcessingException(USER_NOT_FOUND));
        buildImage.setUser(currentUser);
        log.info("*** Save object image into db ***");
        Image image = imageRepository.save(buildImage);
        currentUser.setImage(image);
        userRepository.save(currentUser);
        log.info("*** Image successful set to User ***");
        return image;
    }

    private Path getPath(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path uploadPath = Paths.get(FOLDER_PATH);
        Path filePath = uploadPath.resolve(fileName).normalize();
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }

    private Image getBuildImage(MultipartFile file, Path filePath) {
        return Image.builder()
                .name(file.getOriginalFilename())
                .link(filePath.toString())
                .type(file.getContentType())
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Resource downloadImage(String fileName) throws ServiceProcessingException, IOException {
        log.info("*** Find image name in the db ***");
        Image currentImage = imageRepository.findImageByName(fileName)
                .orElseThrow(() -> new ServiceProcessingException(
                        IMAGE_NOT_FOUND));
        log.info("*** Name successfully found in the db ***");
        Path filePath = Paths.get(FOLDER_PATH).resolve(currentImage.getName()).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists()) {
            log.error("*** Image not found on the server ***");
            throw new ServiceProcessingException(IMAGE_NOT_FOUND);
        }
        return resource;
    }

    @Transactional
    public void deleteImage(String name) throws IOException, ServiceProcessingException {
        log.info("*** Trying to check image name into db***");
        Optional<Image> currentImage = imageRepository.findImageByName(name);
        if (currentImage.isPresent()) {
            imageRepository.deleteImageById(currentImage.get().getId());
            Path filePath = Paths.get(FOLDER_PATH).resolve(name).normalize();
            Files.deleteIfExists(filePath);
        } else {
            log.error("*** Image not found on the server ***");
            throw new ServiceProcessingException(IMAGE_NOT_FOUND);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRES_NEW)
    public Image addImageDescription(ImageDescriptionModel imageModel) throws ServiceProcessingException {
        Optional<Image> currentImage = imageRepository.findImageByName(imageModel.getName());
        currentImage.ifPresent(image -> {
            image.setDescription(imageModel.getDescription());
            imageRepository.save(image);
        });
        if (currentImage.isPresent()) {
            return currentImage.get();
        } else
            log.error("*** Image not found on the server ***");
        throw new ServiceProcessingException(IMAGE_NOT_FOUND);
    }
}
