package com.comp202.ums.Map;

import com.comp202.ums.Dto.user.*;
import com.comp202.ums.Entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Named(value = "userToUserDto")
    UserDto userToUserDto (User user);
    @IterableMapping(qualifiedByName = "userToUserDto")
    List<UserDto> userListToUserDtoList(List<User> users);


}
