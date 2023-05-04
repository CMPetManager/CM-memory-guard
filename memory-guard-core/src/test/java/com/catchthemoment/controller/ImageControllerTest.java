package com.catchthemoment.controller;

/**
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
    void downloadImageShouldReturnResource() throws Exception {
        String imageName = "test-image.jpg";
        String imageContent = "Test image content";
        InputStream inputStream = new ByteArrayInputStream(imageContent.getBytes(StandardCharsets.UTF_8));
        Resource expectedResource = new InputStreamResource(inputStream);
        when(imageService.downloadImage(imageName)).thenReturn(expectedResource);

        ResponseEntity<Object> responseEntity = imageController.downloadImage(imageName);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Resource);
    }
}
 **/