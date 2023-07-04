package com.catchthemoment.service;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.User;
import com.catchthemoment.entity.embedded.Cover;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.repository.AlbumRepository;
import com.catchthemoment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private AlbumMapper mapper;

    @Mock
    private UserRepository userRepository;

    private User user;

    @InjectMocks
    private AlbumService albumService;

    private AlbumModel model1;
    private AlbumModel model2;
    private Album album;
    private Cover cover;

    private List<AlbumModel> albumModelList;

    @BeforeEach
    void setUp() {
        cover = new Cover();
        album = new Album();
        album.setId(1L);
        album.setCover(cover);
        album.setUser(user);
        album.setAlbumName("Garden");
        model1 = new AlbumModel();
        model2 = new AlbumModel();
        albumModelList = List.of(model1, model2);
        user = new User();
        user.setAlbums(List.of(album));
        userRepository.save(user);

    }

    @Test
    void getByAlbum() {
        doReturn(Optional.ofNullable(album)).when(albumRepository).findAlbumById(anyLong());
        doReturn(model1).when(mapper).fromAlbumEntity(album);
        AlbumModel current = albumService.getByAlbum(album.getId());
        assertNotNull(current);
        verify(albumRepository, times(1)).findAlbumById(album.getId());
    }

    @Test
    void findAllAlbumsUser() throws ServiceProcessingException {
        doReturn(Optional.ofNullable(user)).when(userRepository).findUserById(user.getId());
        doReturn(albumModelList).when(mapper).fromAlbumEntities(user.getAlbums());
        var currentList = albumService.findAllAlbumsUser(user.getId());
        assertTrue(currentList.iterator().hasNext());
        verify(userRepository, times(1)).findUserById(user.getId());
    }

    @Test
    void deleteAlbumById() throws ServiceProcessingException {
        doNothing().when(albumRepository).deleteAlbumById(album.getId());
        albumService.deleteAlbumById(album.getId());
    }

    @Test
    void test_getAlbum_By_Name() throws ServiceProcessingException {
        doReturn(Optional.ofNullable(album)).when(albumRepository).findAlbumByName(anyString());
        doReturn(model1).when(mapper).fromAlbumEntity(album);
        var alModel = albumService.getAlbumByName(album.getAlbumName());
        assertNotNull(alModel);
        verify(albumRepository,times(1)).findAlbumByName(album.getAlbumName());
        verify(mapper,atLeastOnce()).fromAlbumEntity(album);
        assertThrows(ServiceProcessingException.class, ()-> albumService.getAlbumByName(eq("")));


    }

}