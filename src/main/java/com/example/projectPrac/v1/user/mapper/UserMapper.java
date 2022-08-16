package com.example.projectPrac.v1.user.mapper;

import com.example.projectPrac.v1.user.dto.UserDto;
import com.example.projectPrac.v1.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default List<UserDto.Response> usersToUsersResponseDtos(List<User> users){
        return users.stream()
                .map(user -> {
                    return new UserDto.Response(
                            user.getUserId(),
                            user.getName(),
                            user.getLocation().getLocation(),
                            user.getCompanyName(),
                            user.getCompanyType().getCompanyType()
                    );
                })
                .collect(Collectors.toList());
    }
}
