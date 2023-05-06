package com.catchthemoment.service;

import com.catchthemoment.entity.User;
import com.catchthemoment.mappers.UserModelMapper;
import com.catchthemoment.model.UserModel;
import com.catchthemoment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class UserEmailServiceTest {


    @Mock
    private UserRepository repo;
    @Mock
    private UserModelMapper mapper;

    @InjectMocks
    private UserEmailService emailService;
    private User user;
    private UserModel readUser;

    @BeforeEach
    void setUp() {
        user = new User(4L ,"Greg","greg041990@mail.ru","turn123ERT");
        readUser = new UserModel();
        readUser.setEmail(user.getEmail());
        repo.save(user);
    }

    /*@Test
    @DisplayName("change to success updated email")
    void changeUserEmail() throws ServiceProcessingException {
        doReturn(Optional.ofNullable(user)).when(repo).findUserById(user.getId());
        readUser.setEmail("jellifish123@mail.ru");
        emailService.changeUserEmail(user.getId(),readUser);
        assertEquals(user.getEmail(),"jellifish123@mail.ru");
        verify(repo,times(1)).findUserById(user.getId());
        assertFalse(readUser.getEmail().isEmpty());
    }*/
}