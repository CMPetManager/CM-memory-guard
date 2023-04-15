package com.catchthemoment.mappers;

import com.catchthemoment.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    com.catchthemoment.entity.Image toEntity(Image imageDto);

    Image toDto(com.catchthemoment.entity.Image image);

}
