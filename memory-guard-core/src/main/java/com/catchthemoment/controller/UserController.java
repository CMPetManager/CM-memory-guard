package com.catchthemoment.controller;

import com.catchthemoment.entity.Image;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.ImageMapper;
import com.catchthemoment.mappers.UserModelMapper;
import com.catchthemoment.model.ImageModel;
import com.catchthemoment.model.UpdatePassword;
import com.catchthemoment.model.UserModel;
import com.catchthemoment.service.ImageService;
import com.catchthemoment.service.UserEmailService;
import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.service.UserService;
import com.catchthemoment.validation.UpdatePasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import static com.catchthemoment.exception.ApplicationErrorEnum.EMPTY_REQUEST;
import static com.catchthemoment.exception.ApplicationErrorEnum.INCORRECT_INPUT;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserController implements UserControllerApiDelegate {

    private static final String RESPONSE_UPDATE_PASSWORD = "Password successfully updated";
    private static final String RESPONSE_DELETE_USER = "User successfully deleted";
    private static final String UPLOAD_IMAGE_CHANGE_SUCCESS = "Upload for changing profile photo was greate";
    private final UserResetPasswordService userResetPasswordService;
    private final UserService userService;
    private final UserEmailService userEmailservice;
    private final UpdatePasswordValidator validator;
    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final UserModelMapper userModelMapper;


    @Override
    public ResponseEntity<UserModel> getUser(Long userId) throws Exception {
        log.info("*** Received a getting user request ***");
        User currentUser = userService.getById(userId);
        log.info("*** User was successful getting  ***");
        return ResponseEntity.ok(userModelMapper.toDto(currentUser));
    }

    @Override
    public ResponseEntity<String> updatePassword(Long userId, UpdatePassword updatePassword) throws Exception {
        if (!validator.isValid(updatePassword)) {
            log.error("*** Password didn't pass validation ***");
            throw new ServiceProcessingException(INCORRECT_INPUT);
        }
        User currentUser = userService.getById(userId);
        log.info("*** Received an update password request with email: {} ***", currentUser.getEmail());
        userResetPasswordService.updatePassword(currentUser, updatePassword.getPassword());
        log.info("*** " + RESPONSE_UPDATE_PASSWORD + " ***");

        return ResponseEntity.ok(RESPONSE_UPDATE_PASSWORD);
    }

    @Override
    public ResponseEntity<Object> updateProfilePhoto(Long usrId, @NotNull MultipartFile image) throws Exception {
        log.info(" Take image request and image should not be null");
        if (!image.isEmpty()) {
            var currentUser = userService.getById(usrId);
            log.info("** user found successfully **", currentUser.getName());
            imageService.deleteImage(currentUser.getImage().getName());
            Image newImage = imageService.uploadImage(usrId, image);
            var newUploadImageModel = imageMapper.toModel(newImage);
            log.info("***" + UPLOAD_IMAGE_CHANGE_SUCCESS + "***");
            return ResponseEntity.ok(newUploadImageModel);
        } else {
            log.error("Image not found or might be empty", image);
            throw new ServiceProcessingException(EMPTY_REQUEST);
        }
    }


    @Override
    public ResponseEntity<String> deleteUser(Long userId) throws Exception {
        log.info("*** Received a delete account request ***");
        userService.deleteUserById(userId);
        log.info("*** " + RESPONSE_DELETE_USER + " ***");
        return ResponseEntity.ok(RESPONSE_DELETE_USER);
    }

    @Override
    public ResponseEntity<Object> addProfilePhoto(Long userId, MultipartFile image) throws Exception {
        log.info("*** Received an upload image request with file name: {} ***", image.getOriginalFilename());
        if (!image.isEmpty()) {
            Image uploadedImage = imageService.uploadImage(userId, image);
            log.info("*** Upload was successful ***");
            ImageModel currentImage = imageMapper.toModel(uploadedImage);
            return ResponseEntity.ok(currentImage);
        } else {
            log.error("*** MultipartFile is empty***");
            throw new ServiceProcessingException(EMPTY_REQUEST);
        }
    }
}