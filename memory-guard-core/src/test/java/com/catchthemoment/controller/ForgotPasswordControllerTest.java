package com.catchthemoment.controller;

import com.catchthemoment.service.UserResetPasswordService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ForgotPasswordController.class)
class ForgotPasswordControllerTest {

    @MockBean
    private UserResetPasswordService resetPasswordService;

    @InjectMocks
    private ForgotPasswordController forgotPasswordController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithUserDetails
    void forgotPasswordForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/forgot-password")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DisplayName("test forgot password should received invalid request should not be successfully")
    @WithUserDetails
    void forgotPassword() {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/forgot-password")
                        .param("forgotPassword", "password")
                        .param("httpRequest", "request"))
                .andExpect(status().is4xxClientError());

    }

}