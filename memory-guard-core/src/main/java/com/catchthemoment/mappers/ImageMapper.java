package com.catchthemoment.mappers;

import com.catchthemoment.entity.Image;
import com.catchthemoment.model.ImageModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * For mapping DTO models to @entities and come back side
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    Image toEntity(ImageModel imageModel);

    ImageModel toModel(Image image);

}
