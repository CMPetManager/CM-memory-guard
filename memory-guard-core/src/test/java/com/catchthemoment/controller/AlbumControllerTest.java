package com.catchthemoment.controller;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.User;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(AlbumController.class)
@Import(AlbumController.class)
class AlbumControllerTest {

    private final String BASE_URI = "/albums/";

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AlbumService service;

    @MockBean
    private AlbumRepository albumRepository;
    //   @Mock
    //   private AlbumControllerApiDelegate delegate;
//    @Mock
    //   private AlbumControllerApi albumControllerApi;
    //   @Mock
//    private AlbumMapper mapper;
    private AlbumModel model;
    private Album fakeEntity;
    private User fakeUser;
    private User fakeUser2;
    private List<User> userList;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AlbumController controller;

    @BeforeEach
    void setUpTest() throws ServiceProcessingException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

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
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.albumRepository.save(fakeEntity);
        assertNotNull(albumRepository);
        controller = new AlbumController(service);
    }

/**
    @Test
    void getAlbumByName() throws Exception {
        String mockName = fakeEntity.getAlbumName();
        when(service.getAlbumByName(mockName)).thenReturn(model);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI.concat(mockName))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name").value(mockName));

    }


    @Test
    void deleteAlbumById_Should_remove_album() throws Exception {
        Long fakeId = fakeEntity.getId();
        doNothing().when(service).deleteAlbumById(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URI.concat(String.valueOf(fakeId)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotImplemented());
    }
    **/

}