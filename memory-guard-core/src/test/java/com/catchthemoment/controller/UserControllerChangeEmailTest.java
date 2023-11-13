package com.catchthemoment.controller;

import com.catchthemoment.model.ChangeEmailRequestModel;
import com.catchthemoment.model.UserModel;
import com.catchthemoment.repository.UserRepository;
import com.catchthemoment.service.UserEmailService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserChangeEmailController.class)
class UserControllerChangeEmailTest {
    public static final Long US_ID = 2L;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private UserEmailService userEmailService;


    @Test
    @WithMockUser
    void updateExistsEmail() throws Exception {
        var fakeUser = new ChangeEmailRequestModel();
        fakeUser.setNewEmail("froddo1990@mail.ru");

        mockMvc.perform(post("/users/change-email", US_ID).with(csrf()).param("newEmail",fakeUser.getNewEmail()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<UserModel> captor = ArgumentCaptor.forClass(UserModel.class);
        verify(userEmailService, times(1)).changeUserEmail(captor.capture().getId(), fakeUser);
        assertThat(captor.getValue().getEmail()).isEqualTo("froddo1990@mail.ru");


    }
}