package com.catchthemoment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;

import com.catchthemoment.entity.Image;
import com.catchthemoment.mappers.ImageMapper;
import com.catchthemoment.model.ImageModel;
import com.catchthemoment.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @Mock
    private ImageMapper imageMapper;

    private ImageController imageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(imageService, imageMapper);
    }

    @Test
    void testUploadImage() throws Exception {
        MultipartFile file = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "test image".getBytes());
        Image image = new Image();
        image.setName("test.jpg");

        doReturn(image).when(imageService).uploadImage(any(MultipartFile.class));
        doReturn(new ImageModel()).when(imageMapper).toModel(any(Image.class));

        ResponseEntity<ImageModel> response = imageController.uploadImage(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDownloadImage() throws Exception {
        byte[] data = "test image".getBytes();
        String name = "test.jpg";

        doReturn(data).when(imageService).downloadImage(name);

        ResponseEntity<List<byte[]>> response = imageController.downloadImage(name);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(data.length, response.getBody().get(0).length);
    }
}