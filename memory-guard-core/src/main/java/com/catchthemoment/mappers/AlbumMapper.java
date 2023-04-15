package com.catchthemoment.mappers;

import com.catchthemoment.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumMapper {

    com.catchthemoment.entity.Album toEntity(Album albumDto);

    Album toDto(com.catchthemoment.entity.Album album);
}
