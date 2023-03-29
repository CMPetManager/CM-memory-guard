package com.catchthemoment.mappers;

import com.catchthemoment.dto.UserDTO;
import com.catchthemoment.entity.User;
import com.catchthemoment.model.UserAPI;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(User user);

    UserAPI toDto(User user);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "email", source = "user.email")
    UserDTO toUserDTO(User user);

    @Mapping(target = "name",source = "userDTO.name")
    @Mapping(target = "password",source = "userDTO.password")
    @Mapping(target = "email",source = "userDTO.email")
    User fromUserDTO(UserDTO userDTO);
}
