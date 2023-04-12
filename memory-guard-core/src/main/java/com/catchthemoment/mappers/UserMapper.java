package com.catchthemoment.mappers;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.UserDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "reset_password_token", source = "user.resetPasswordToken")
    UserDTO toUserDTO(User user);

    @InheritConfiguration
    User fromUserDTO(UserDTO userDTO);
}
