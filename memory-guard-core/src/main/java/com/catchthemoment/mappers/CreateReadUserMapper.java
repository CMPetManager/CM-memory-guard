package com.catchthemoment.mappers;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.CreateReadUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateReadUserMapper {

    User toEntity(CreateReadUser createReadUser);

    CreateReadUser toDto(User user);
}
