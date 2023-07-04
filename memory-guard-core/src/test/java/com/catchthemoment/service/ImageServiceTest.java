package com.catchthemoment.service;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.Image;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.repository.ImageRepository;
import com.catchthemoment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith({MockitoExtension.class})
class ImageServiceTest {
    private static final Long ID = 1L;
    private static final String FOLDER_PATH = "src/test/resources/";

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private AlbumMapper albumMapper;
    @InjectMocks
    private ImageService imageService;

    @Test
    void uploadImage_withValidAlbumModelAndMultipartFile_shouldReturnSavedImage() throws IOException, ServiceProcessingException {
        AlbumModel albumModel = new AlbumModel();
        MockMultipartFile file = getMockMultipartFile();
        when(imageRepository.findImageByName(anyString())).thenReturn(Optional.empty());
        when(albumMapper.fromAlbumModel(any())).thenReturn(new Album());
        when(imageRepository.save(any())).thenReturn(new Image());

        Image savedImage = imageService.uploadImage(albumModel, file);

        verify(imageRepository).findImageByName(anyString());
        verify(albumMapper).fromAlbumModel(any());
        verify(imageRepository).save(any());
        assertThat(savedImage).isNotNull();
    }

    @Test
    void uploadImage_withDuplicateImageName_shouldThrowException() throws IOException, ServiceProcessingException {
        AlbumModel albumModel = new AlbumModel();
        MockMultipartFile file = getMockMultipartFile();
        when(imageRepository.findImageByName(anyString())).thenReturn(Optional.of(new Image()));

        assertThatThrownBy(() -> imageService.uploadImage(albumModel, file))
                .isInstanceOf(ServiceProcessingException.class)
                .hasMessage("Already exists");
    }

    @Test
    void uploadImage_withValidUserIdAndMultipartFile_shouldReturnSavedImage() throws IOException, ServiceProcessingException {
        MockMultipartFile file = getMockMultipartFile();
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.of(new User()));
        when(imageRepository.save(any())).thenReturn(new Image());

        Image savedImage = imageService.uploadImage(ID, file);

        verify(userRepository).findUserById(anyLong());
        verify(imageRepository).save(any());
        assertThat(savedImage).isNotNull();
    }

    @Test
    void downloadImageIfExists() throws ServiceProcessingException, IOException {
        Image expectedImage = getImage(getMockMultipartFile());
        doReturn(Optional.of(expectedImage)).when(imageRepository).findImageByName(expectedImage.getName());

        ByteArrayResource resource = (ByteArrayResource) imageService.downloadImage(expectedImage.getName());

        assertNotNull(resource);
        assertEquals(resource.getByteArray().length, getMockMultipartFile().getBytes().length);

    }

    @Test
    void downloadImageThrowExceptionIfImageDoesNotExists(){
        assertThrows(ServiceProcessingException.class,
                () -> imageService.downloadImage(getMockMultipartFile().getOriginalFilename()));
    }

    @Test
    void testDeleteImageIfExists() throws IOException, ServiceProcessingException {
        String imageName = "testImage.jpg";
        Image image = Image.builder().id(ID).name(imageName).build();
        Path filePath = Paths.get(FOLDER_PATH).resolve(imageName).normalize();

        when(imageRepository.findImageByName(imageName)).thenReturn(Optional.of(image));

        imageService.deleteImage(imageName);

        verify(imageRepository).deleteImageById(image.getId());
        assertTrue(Files.notExists(filePath));
    }

    @Test
    void testDeleteImageIfNotFound() throws IOException, ServiceProcessingException {
        String imageName = "testImage.jpg";

        when(imageRepository.findImageByName(imageName)).thenReturn(Optional.empty());

        assertThrows(ServiceProcessingException.class, () -> imageService.deleteImage(imageName));
        verify(imageRepository, never()).deleteImageById(anyLong());
    }

    private static MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("foo", "foo.png", MediaType.IMAGE_PNG_VALUE,
                "Hello World" .getBytes());
    }

    private Image getImage(MockMultipartFile file) {
        return Image.builder()
                .id(ID)
                .name(file.getOriginalFilename())
                .link(FOLDER_PATH + file.getOriginalFilename())
                .build();
    }
}