package com.catchthemoment.controller;

import com.catchthemoment.entity.Image;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.ImageMapper;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.model.ImageDescriptionModel;
import com.catchthemoment.model.ImageModel;
import com.catchthemoment.service.AlbumService;
import com.catchthemoment.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.catchthemoment.exception.ApplicationErrorEnum.EMPTY_REQUEST;
import static com.catchthemoment.exception.ApplicationErrorEnum.IMAGE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageControllerTest {
    private static final Long ID = 1L;

    @Mock
    private ImageService imageService;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    private AlbumService albumService;

    private ImageController imageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(imageService, imageMapper, albumService);
    }

    @Test
    void testUploadImageSuccess() throws Exception {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        AlbumModel mockAlbumModel = new AlbumModel();
        when(albumService.getByAlbum(ID)).thenReturn(mockAlbumModel);
        Image mockImage = mock(Image.class);
        when(imageService.uploadImage(mockAlbumModel, mockFile)).thenReturn(mockImage);
        ImageModel mockImageModel = new ImageModel();
        when(imageMapper.toModel(mockImage)).thenReturn(mockImageModel);

        ResponseEntity<ImageModel> response = imageController.uploadImage(ID, mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockImageModel, response.getBody());
    }

    @Test
    void testUploadImageEmptyRequest() throws Exception {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(true);
        ServiceProcessingException expectedException = new ServiceProcessingException(
                EMPTY_REQUEST.getCode(),
                EMPTY_REQUEST.getMessage()
        );
        assertThrows(ServiceProcessingException.class, () -> imageController.uploadImage(ID, mockFile));
        verify(albumService, never()).getByAlbum(anyLong());
        verify(imageService, never()).uploadImage((Long) any(), any());
        verify(imageMapper, never()).toModel(any());
    }

    @Test
    void testUploadImageEmptyFile() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () ->
                imageController.uploadImage(ID, file));

        assertEquals(EMPTY_REQUEST.getCode(), exception.getCode());
        assertEquals(EMPTY_REQUEST.getMessage(), exception.getMessage());
    }

    @Test
    void downloadImageShouldReturnResource() throws Exception {
        String imageName = "test-image.jpg";
        String imageContent = "Test image content";
        InputStream inputStream = new ByteArrayInputStream(imageContent.getBytes(StandardCharsets.UTF_8));
        Resource expectedResource = new InputStreamResource(inputStream);
        when(imageService.downloadImage(imageName)).thenReturn(expectedResource);

        ResponseEntity<?> responseEntity = imageController.downloadImage(imageName);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Resource);
    }

    @Test
    void testDeleteOneImage() throws Exception {
        List<String> requestBody = Collections.singletonList("image1.jpg");

        ResponseEntity<Object> response = imageController.deleteImages(requestBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Images successfully deleted", response.getBody());
        verify(imageService, times(1)).deleteImage("image1.jpg");
    }

    @Test
    void testDeleteMultipleImages() throws Exception {
        List<String> requestBody = Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg");

        ResponseEntity<Object> response = imageController.deleteImages(requestBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Images successfully deleted", response.getBody());
        verify(imageService, times(1)).deleteImage("image1.jpg");
        verify(imageService, times(1)).deleteImage("image2.jpg");
        verify(imageService, times(1)).deleteImage("image3.jpg");
    }

    @Test
    void testDeleteNonexistentImage() throws Exception {
        List<String> requestBody = Collections.singletonList("nonexistent_image.jpg");
        doThrow(new ServiceProcessingException(IMAGE_NOT_FOUND.getCode(), IMAGE_NOT_FOUND.getMessage()))
                .when(imageService).deleteImage("nonexistent_image.jpg");

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class,
                () -> imageController.deleteImages(requestBody));

        assertEquals("Image not found", exception.getMessage());
        verify(imageService, times(1)).deleteImage("nonexistent_image.jpg");
    }

    @Test
    void testDeleteEmptyRequestBody() throws Exception {
        List<String> requestBody = Collections.emptyList();

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class,
                () -> imageController.deleteImages(requestBody));

        assertEquals("The request is empty", exception.getMessage());
    }

    @Test
    void testAddDescription() throws Exception {
        ImageDescriptionModel imageDescription = new ImageDescriptionModel();
        imageDescription.setName("testImage.jpg");
        imageDescription.setDescription("This is a test image");
        Image image = new Image();
        when(imageService.addImageDescription(imageDescription)).thenReturn(image);
        when(imageMapper.toModel(image)).thenReturn(new ImageModel());

        ResponseEntity<ImageModel> response = imageController.addDescription(imageDescription);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}