package com.catchthemoment.service;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.User;
import com.catchthemoment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserServiceTest {

    private static final Long USER_ID = 1L;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void getByIdIfUserExists() throws ServiceProcessingException {
        User expectedResult = getUser();
        doReturn(Optional.of(expectedResult)).when(userRepository).findById(USER_ID);

        User actualResult = userService.getById(USER_ID);

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);

        verify(userRepository).findById(USER_ID);
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

    private User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setName("Ivan");
        user.setEmail("hello@gmail.com");
        user.setPassword("111");

        return user;
    }

}