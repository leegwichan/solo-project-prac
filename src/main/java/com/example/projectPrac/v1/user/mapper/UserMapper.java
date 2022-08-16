package com.example.projectPrac.v1.user.mapper;

import com.example.projectPrac.v1.user.dto.UserDto;
import com.example.projectPrac.v1.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDto.Response> usersToUsersResponseDtos(List<User> users);
}
