package com.catchthemoment.mappers;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserModelMapper {

    User toEntity(UserModel userModel);

    UserModel toDto(User user);
}
