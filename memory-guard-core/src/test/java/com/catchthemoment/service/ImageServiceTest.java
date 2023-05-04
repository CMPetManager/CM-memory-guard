package com.catchthemoment.service;

/**
@Slf4j
@ExtendWith({MockitoExtension.class})
class ImageServiceTest {
    private static final Long IMAGE_ID = 1L;
    private static final String FOLDER_PATH = "C:\\Users\\Admin\\gitlab\\";

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @Test
    void uploadImage() throws ServiceProcessingException, IOException {
        MockMultipartFile file = getMockMultipartFile();
        Image expectedImage = getImage(file);
        doReturn(empty()).when(imageRepository).findImageByName(file.getOriginalFilename());
        doReturn(expectedImage).when(imageRepository).save(any());

        Image currentImage = imageService.uploadImage(file);

        assertNotNull(currentImage);
        assertEquals(currentImage, expectedImage);
    }

    @Test
    void uploadImageThrowExceptionIfImageIsAlreadyExists() {
        Image image = getImage(getMockMultipartFile());
        doReturn(Optional.of(image)).when(imageRepository)
                .findImageByName(image.getName());

        assertThrows(ServiceProcessingException.class, () -> imageService.uploadImage(getMockMultipartFile()));
    }

    @Test
    void downloadImageIfExists() throws ServiceProcessingException, IOException {
        Image expectedImage = getImage(getMockMultipartFile());
        doReturn(Optional.of(expectedImage)).when(imageRepository).findImageByName(expectedImage.getName());

        Resource resource = imageService.downloadImage(expectedImage.getName());

        assertNotNull(resource);
        assertEquals(resource.getContentAsByteArray().length, getMockMultipartFile().getBytes().length);

    }

    @Test
    void downloadImageThrowExceptionIfImageDoesNotExists(){
        assertThrows(ServiceProcessingException.class,
                () -> imageService.downloadImage(getMockMultipartFile().getOriginalFilename()));
    }



    private static MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("foo", "foo.png", MediaType.IMAGE_PNG_VALUE,
                "Hello World" .getBytes());
    }

    private Image getImage(MockMultipartFile file) {
        return Image.builder()
                .id(IMAGE_ID)
                .name(file.getOriginalFilename())
                .link(FOLDER_PATH + file.getOriginalFilename())
                .build();
    }

}
 **/