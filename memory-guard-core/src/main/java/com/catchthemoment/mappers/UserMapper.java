package com.catchthemoment.mappers;

import com.catchthemoment.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(com.catchthemoment.model.User user);

    com.catchthemoment.model.User toDto(User user);
}
