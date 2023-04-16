package com.catchthemoment.mapper;

import com.catchthemoment.entity.Album;
import com.catchthemoment.mappers.AlbumMapper;
import com.catchthemoment.model.AlbumModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//TODO fix mapping user to album
public class AlbumMapperTest {

    private AlbumMapper albumMapper = Mappers.getMapper(AlbumMapper.class);

    @Test
    void mapperTest(){
        assertNotNull(albumMapper);

        AlbumModel api = new AlbumModel();
        api.setId(4L);

        Album entity = albumMapper.fromAlbumModel(api);

        assertEquals(api.getId(),entity.getId());

    }
}
