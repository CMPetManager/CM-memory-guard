package com.catchthemoment.controller;

import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.UpdatePassword;
import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.service.UserService;
import com.catchthemoment.validation.UpdatePasswordValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final Long USER_ID = 1L;

    @Mock
    private UserResetPasswordService userResetPasswordService;

    @Mock
    private UserService userService;

    @Mock
    private UpdatePasswordValidator validator;

    @InjectMocks
    private UserController userController;

    @Test
    void testUpdatePasswordSuccess() throws Exception {
        String newPassword = "newPassword123";
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setPassword(newPassword);
        User currentUser = new User();
        currentUser.setEmail("test@test.com");

        doReturn(currentUser).when(userService).getById(USER_ID);
        doReturn(true).when(validator).isValid(updatePassword);

        ResponseEntity<String> response = userController.updatePassword(USER_ID, updatePassword);

        verify(userResetPasswordService).updatePassword(currentUser, newPassword);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password successfully updated", response.getBody());
    }

    @Test
    void testUpdatePasswordWithInvalidInput() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setPassword("");

        doReturn(false).when(validator).isValid(updatePassword);

        assertThrows(ServiceProcessingException.class, () -> userController.updatePassword(USER_ID, updatePassword));

        verifyNoInteractions(userResetPasswordService);
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        ResponseEntity<String> response = userController.deleteUser(USER_ID);

        verify(userService).deleteUserById(USER_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User successfully deleted", response.getBody());
    }
}