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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    private User fakeUser;
    private User fakeUser2;
    private List<User> userList;


    private MockMvc mockMvc;

    @InjectMocks
    private AlbumController controller;

    @BeforeEach
    void setUpTest() throws ServiceProcessingException {

        fakeEntity = new Album();
        fakeEntity.setAlbumName("Frogg");
        fakeEntity.setUser(fakeUser);
        fakeEntity.setId(2L);
        fakeEntity.setColor("Yellow");
        fakeUser = new User();
        fakeUser.setEmail("nep041990@mail.ru");
        fakeUser.setPassword("12345ert");
        fakeUser.setName("Pretty");
        fakeUser.setId(2L);
        fakeUser2 = new User();
        fakeUser2.setEmail("marco041990@mail.ru");
        fakeUser2.setPassword("2GJL48");
        fakeUser2.setName("Boris");
        fakeUser2.setId(3L);
        userList = List.of(fakeUser, fakeUser2);
        model = new AlbumModel();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.albumRepository.save(fakeEntity);
        assertNotNull(albumRepository);
    }

    /*@Test
    void getAlbumByName() throws Exception {
        String name = fakeEntity.getAlbumName();
        given(service.getAlbumByName(name)).willReturn(model);
        mockMvc.perform(MockMvcRequestBuilders.get("/albums/{name}", name).param("name", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));

    }*/

    @AfterEach
    public void tearDownTests() {
        fakeUser = null;
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