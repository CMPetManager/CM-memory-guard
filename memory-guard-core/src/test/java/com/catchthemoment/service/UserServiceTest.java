package com.catchthemoment.service;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.entity.User;
import com.catchthemoment.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserServiceTest {


    private static final Long USER_ID = 1L;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserConfirmMailService confirmMailService;

    @InjectMocks
    private UserService userService;

    @Test
    void registration() throws Exception {
        User expectedUser = getUser();
        doReturn(expectedUser).when(userRepository).save(expectedUser);
        doReturn(Optional.empty()).when(userRepository).findUserByEmail(expectedUser.getEmail());
        doReturn(expectedUser.getPassword()).when(passwordEncoder).encode(expectedUser.getPassword());

        User registeredUser = userService.create(expectedUser,
                expectedUser.getConfirmationResetToken());

        assertEquals(expectedUser, registeredUser);
    }

    @Test
    void registrationExceptionIfUSerAlreadyExists() {
        User user = getUser();
        doReturn(Optional.of(user)).when(userRepository).findUserByEmail(user.getEmail());
        assertThrows(ServiceProcessingException.class, () -> userService.create(user, user.getConfirmationResetToken()));
    }

    @Test
    void getByIdIfUserExists() throws ServiceProcessingException {
        User expectedResult = getUser();
        doReturn(Optional.of(expectedResult)).when(userRepository).findUserById(USER_ID);

        User actualResult = userService.getById(USER_ID);

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);

        verify(userRepository).findUserById(USER_ID);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getByIdThrowException() {
        assertThrows(ServiceProcessingException.class, () -> userService.getById(USER_ID));
    }

    @Test
    void getByEmailIfUserExists() throws ServiceProcessingException {
        User expectedResult = getUser();
        doReturn(Optional.of(expectedResult)).when(userRepository).findUserByEmail(expectedResult.getEmail());

        User actualResult = userService.getByEmail(expectedResult.getEmail());

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);

        verify(userRepository).findUserByEmail(expectedResult.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getByEmailThrowException() {
        assertThrows(ServiceProcessingException.class, () -> userService.getByEmail(getUser().getEmail()));
    }

    @Test
    void deleteUserByIdSuccessful() {
        User user = getUser();

        doReturn(Optional.of(user)).when(userRepository).findUserById(USER_ID);
        doNothing().when(userRepository).deleteById(USER_ID);

        assertDoesNotThrow(() -> userService.deleteUserById(USER_ID));
    }

    @Test
    void deleteUserByIdThrowExceptionIfUserDoesNotExists() {
        assertThrows(ServiceProcessingException.class, () -> userService.deleteUserById(USER_ID));
    }


    private User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setName("Ivan");
        user.setEmail("hello@gmail.com");
        user.setPassword("111");
        String randomCode = RandomString.make(20);
        user.setConfirmationResetToken(randomCode);

        return user;
    }

}