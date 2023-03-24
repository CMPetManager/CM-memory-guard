package com.catchthemoment.mappers;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.UserAPI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(User user);

    UserAPI toDto(User user);
}
