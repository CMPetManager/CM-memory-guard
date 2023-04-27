package com.catchthemoment.mappers;

import com.catchthemoment.entity.Album;
import com.catchthemoment.entity.User;
import com.catchthemoment.model.AlbumModel;
import com.catchthemoment.model.UserModel;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumMapper {

    @Mapping(target = "user", qualifiedByName = "mapUserModel", ignore = true)
    AlbumModel fromAlbumEntity(Album album);

    @Mapping(target = "user", qualifiedByName = "mapUserEntity", ignore = true)
    Album fromAlbumModel(AlbumModel albumModel);

    List<AlbumModel> fromAlbumEntities(List<Album> albumList);

    @Named("mapUserModel")
    default List<UserModel> mapUsers(User user) {
        return new ArrayList<>();
    }

    @Named("mapUserEntity")
    default User mapFromUserModelList(List<UserModel> models) {
        UserModel model = models.stream()
                .filter(userModel -> userModel.getId().intValue() > 0)
                .findFirst().get();
        User mapUser = fromUserModel(model);
        return mapUser;
    }

    User fromUserModel(UserModel userModel);



}
