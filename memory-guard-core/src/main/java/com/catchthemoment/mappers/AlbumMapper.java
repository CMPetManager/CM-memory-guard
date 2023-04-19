package com.catchthemoment.mappers;

import com.catchthemoment.entity.Album;
import com.catchthemoment.model.AlbumModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumMapper {

    Album toEntity(AlbumModel albumModel);

    AlbumModel toModel(Album album);
}
