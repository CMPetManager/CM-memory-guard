package com.catchthemoment.controller;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.service.AlbumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
//TODO fix controller test
class AlbumControllerTest {

    @Mock
    private AlbumService service;

    @Mock
    private AlbumRepository albumRepository;
    private AlbumModel model;
    private Album fakeEntity;
    private User fakeuser;
    private User fakeUser2;
    private List<User> userList;


    private MockMvc mockMvc;

    @InjectMocks
    private AlbumController controller;

    @BeforeEach
    void setUpTest() throws ServiceProcessingException {

        fakeEntity = new Album();
        fakeEntity.setAlbumName("Frogg");
        fakeEntity.setUser(fakeuser);
        fakeEntity.setId(2L);
        fakeEntity.setColor("Yellow");
        fakeuser = new User();
        fakeuser.setEmail("nep041990@mail.ru");
        fakeuser.setPassword("12345ert");
        fakeuser.setName("Pretty");
        fakeuser.setId(2L);
        fakeUser2 = new User();
        fakeUser2.setEmail("marco041990@mail.ru");
        fakeUser2.setPassword("2GJL48");
        fakeUser2.setName("Boris");
        fakeUser2.setId(3L);
        userList = List.of(fakeuser, fakeUser2);
        model = new AlbumModel();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.albumRepository.save(fakeEntity);
        assertNotNull(albumRepository);


    }

    @Test
    void getAlbumByName() throws Exception {
        String name = fakeEntity.getAlbumName();
        given(service.getAlbumByName(name)).willReturn(model);
        mockMvc.perform(MockMvcRequestBuilders.get("/albums/{name}", name).param("name", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));

    }


    @AfterEach
    public void tearDownTests() {
        fakeuser = null;
        fakeUser2 = null;
        userList = null;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}