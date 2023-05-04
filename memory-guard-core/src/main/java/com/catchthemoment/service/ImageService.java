package com.catchthemoment.service;

/**
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private static final String FOLDER_PATH = "C:\\Users\\Admin\\gitlab\\";

    private final ImageRepository imageRepository;

    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException, ServiceProcessingException {
        log.info("*** Checking the image name for uniqueness ***");
        if (imageRepository.findImageByName(file.getOriginalFilename()).isPresent()) {
            log.error("*** This image is already exists ***");
            throw new ServiceProcessingException(ILLEGAL_STATE.getCode(), ILLEGAL_STATE.getMessage());
        }
        log.info("*** Validation passed, This imageName doesn't exist in the database ***");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path uploadPath = Paths.get(FOLDER_PATH);
        Path filePath = uploadPath.resolve(fileName).normalize();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Image buildImage = getBuildImage(file, filePath);
        log.info("*** Save object image into db ***");

        Image image = imageRepository.save(buildImage);
        log.info("*** Image successful saved ***");

        return image;
    }

    public Resource downloadImage(String fileName) throws ServiceProcessingException, IOException {
        log.info("*** Find image name in the db ***");
        Image currentImage = imageRepository.findImageByName(fileName)
                .orElseThrow(() -> new ServiceProcessingException(
                        IMAGE_NOT_FOUND.getCode(),
                        IMAGE_NOT_FOUND.getMessage()));
        log.info("*** Name successfully found in the db ***");

        Path filePath = Paths.get(FOLDER_PATH).resolve(currentImage.getName()).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            log.error("*** Image not found on the server ***");
            throw new ServiceProcessingException(IMAGE_NOT_FOUND.getCode(), IMAGE_NOT_FOUND.getMessage());
        }
        return resource;
    }

    private static Image getBuildImage(MultipartFile file, Path filePath) {
        return Image.builder()
                .name(file.getOriginalFilename())
                .link(filePath.toString())
                .build();
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
            throw new ServiceProcessingException(IMAGE_NOT_FOUND.getCode(), IMAGE_NOT_FOUND.getMessage());
        }
    }

    public Image addDescription(ImageModel imageModel) {
        Optional<Image> currentImage = imageRepository.findImageByName(imageModel.getName());
        currentImage.ifPresent(image -> {
            image.setDescription(imageModel.getDescription());
            imageRepository.save(image);
        });
        return currentImage.get();
    }
}
 **/
