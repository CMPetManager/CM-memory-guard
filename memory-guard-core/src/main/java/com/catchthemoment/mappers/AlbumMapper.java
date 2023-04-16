package com.catchthemoment.mappers;

import com.catchthemoment.entity.Album;
import com.catchthemoment.model.AlbumModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumMapper {

    @Mappings({
            @Mapping(target = "pages", ignore = true),
            @Mapping(target = "user",ignore = true)
    })
    AlbumModel fromAlbumEntity(Album album);

    @Mappings({
            @Mapping(target = "pages", ignore = true),
            @Mapping(target = "user",ignore = true)
    })
    Album fromAlbumModel(AlbumModel albumModel);

    List<AlbumModel> fromAlbumEntities(List<Album> albumList);
}
