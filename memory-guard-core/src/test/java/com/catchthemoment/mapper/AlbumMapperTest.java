package com.catchthemoment.mapper;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.User;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.model.AlbumModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class AlbumMapperTest {

    private final AlbumMapper albumMapper = Mappers.getMapper(AlbumMapper.class);

    private final User user1 = new User(4L, "Marko", "hecler1990@mail.ru", "hecler1234");
    private  Album fakeALbum ;


    @Test
    void mapperTest() {
        fakeALbum = new Album();
        fakeALbum.setAlbumName("Moove");
        fakeALbum.setId(2L);
        fakeALbum.setColor("Red");
        assertNotNull(albumMapper);

        AlbumModel api = new AlbumModel();
        Album entity = albumMapper.fromAlbumModel(api);
        AlbumModel mod = albumMapper.fromAlbumEntity(fakeALbum);

        assertEquals(api.getId(), entity.getId());
       // assertEquals(mod.getName(),"Moove");
    }
}
