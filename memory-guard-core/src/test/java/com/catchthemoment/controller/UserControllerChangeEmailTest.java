package com.catchthemoment.controller;

import com.catchthemoment.model.ChangeEmailRequestModel;
import com.catchthemoment.repository.UserRepository;
import com.catchthemoment.service.UserEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserChangeEmailController.class)
@Import(UserChangeEmailControllerApiController.class)
class UserControllerChangeEmailTest {
    public static final Long US_ID = 2L;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @Autowired
    private ObjectMapper mapper;


    @MockBean
    private UserEmailService userEmailService;


    @Test
    @WithMockUser
    void updateExistsEmail() throws Exception {
        var fakeUser = new ChangeEmailRequestModel();
        fakeUser.setNewEmail("froddo1990@mail.ru");

        doNothing().when(userEmailService).changeUserEmail(US_ID,fakeUser);

        mockMvc.perform(post("/users/change-email/{usrId}", US_ID).with(csrf())
                        .content(mapper.writeValueAsString(fakeUser))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

       // ArgumentCaptor<ChangeEmailRequestModel> captor = ArgumentCaptor.forClass(ChangeEmailRequestModel.class);
      //  assertThat(captor.getValue().getNewEmail()).isEqualTo("froddo1990@mail.ru");


    }
}