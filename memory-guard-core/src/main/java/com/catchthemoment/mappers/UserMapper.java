package com.catchthemoment.mappers;

import com.catchthemoment.dto.UserDTO;
import com.catchthemoment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "reset_password_token", source = "user.resetPasswordToken")
    UserDTO toUserDTO(User user);

    @Mapping(target = "name",source = "userDTO.name")
    @Mapping(target = "password",source = "userDTO.password")
    @Mapping(target = "email",source = "userDTO.email")
    @Mapping(target = "resetPasswordToken",source = "userDTO.reset_password_token")
    User fromUserDTO(UserDTO userDTO);
}
