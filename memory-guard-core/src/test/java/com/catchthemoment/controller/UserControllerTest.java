package com.catchthemoment.controller;

import com.catchthemoment.entity.Image;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.ImageMapper;
import com.catchthemoment.model.ImageModel;
import com.catchthemoment.model.UpdatePassword;
import com.catchthemoment.service.ImageService;
import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.service.UserService;
import com.catchthemoment.validation.UpdatePasswordValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static com.catchthemoment.exception.ApplicationErrorEnum.EMPTY_REQUEST;
import static com.catchthemoment.exception.ApplicationErrorEnum.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final Long ID = 1L;

    @Mock
    private UserResetPasswordService userResetPasswordService;

    @Mock
    private UserService userService;

    @Mock
    private UpdatePasswordValidator validator;
    @Mock
    private ImageService imageService;
    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void testUpdatePasswordSuccess() throws Exception {
        String newPassword = "newPassword123";
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setPassword(newPassword);
        User currentUser = new User();
        currentUser.setEmail("test@test.com");

        doReturn(currentUser).when(userService).getById(ID);
        doReturn(true).when(validator).isValid(updatePassword);

        ResponseEntity<String> response = userController.updatePassword(ID, updatePassword);

        verify(userResetPasswordService).updatePassword(currentUser, newPassword);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password successfully updated", response.getBody());
    }

    @Test
    void testUpdatePasswordWithInvalidInput() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setPassword("");

        doReturn(false).when(validator).isValid(updatePassword);

        assertThrows(ServiceProcessingException.class, () -> userController.updatePassword(ID, updatePassword));

        verifyNoInteractions(userResetPasswordService);
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        ResponseEntity<String> response = userController.deleteUser(ID);

        verify(userService).deleteUserById(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User successfully deleted", response.getBody());
    }

    @Test
    void testDeleteNonExistingUser() throws Exception {
        doThrow(new ServiceProcessingException(USER_NOT_FOUND.getCode(),
                USER_NOT_FOUND.getMessage())).when(userService).deleteUserById(ID);

        assertThrows(ServiceProcessingException.class, () -> userController.deleteUser(ID));
        verify(userService, times(1)).deleteUserById(ID);
    }

    @Test
    void testDeleteUserResponseCode() throws Exception {
        doNothing().when(userService).deleteUserById(ID);
        ResponseEntity<String> responseEntity = userController.deleteUser(ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testAddProfilePhotoSuccess() throws Exception {
        MockMultipartFile mockMultipartFile = getMockMultipartFile();
        Image image = getImage(mockMultipartFile);
        ImageModel model = new ImageModel();
        model.setId(ID);
        model.setName("foo.png");
        model.setLink("foo.png");
        when(imageService.uploadImage(ID, mockMultipartFile)).thenReturn(image);
        when(imageMapper.toModel(any())).thenReturn(model);

        ResponseEntity<Object> responseEntity = userController.addProfilePhoto(ID, mockMultipartFile);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ImageModel);

        ImageModel currentImage = (ImageModel) responseEntity.getBody();

        assertEquals(image.getId(), currentImage.getId());
    }

    @Test
    void testAddProfilePhotoEmptyImage() throws Exception {

        MockMultipartFile file = new MockMultipartFile("image.jpg", new byte[]{});
        ServiceProcessingException exception = assertThrows(
                ServiceProcessingException.class, () -> userController.addProfilePhoto(ID, file));

        assertEquals(EMPTY_REQUEST.getCode(), exception.getCode());
        assertEquals(EMPTY_REQUEST.getMessage(), exception.getMessage());
    }

    private static MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("foo", "foo.png", MediaType.IMAGE_PNG_VALUE,
                "Hello World".getBytes());
    }

    private Image getImage(MockMultipartFile file) {
        return Image.builder()
                .id(ID)
                .name(file.getOriginalFilename())
                .link(file.getOriginalFilename())
                .build();
    }
}