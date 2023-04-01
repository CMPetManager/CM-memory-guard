package com.catchthemoment.mappers;

import com.catchthemoment.entity.User;
import com.catchthemoment.model.UserAPI;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserAPI userAPI);

    UserAPI toDto(User user);
}
