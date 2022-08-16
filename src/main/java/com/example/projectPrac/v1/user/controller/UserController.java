package com.example.projectPrac.v1.user.controller;

import com.example.projectPrac.v1.companyLocation.entity.Location;
import com.example.projectPrac.v1.dto.MultiResponseDto;
import com.example.projectPrac.v1.user.dto.UserDto;
import com.example.projectPrac.v1.user.entity.User;
import com.example.projectPrac.v1.user.mapper.UserMapper;
import com.example.projectPrac.v1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity getUsers(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size){

        Page<User> users = userService.findUsers(page-1, size);
        List<UserDto.Response> data = mapper.usersToUsersResponseDtos(users.getContent());
        return new ResponseEntity(new MultiResponseDto(data, users), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity getUsersByCondition(@Positive @RequestParam int page,
                                              @Positive @RequestParam int size,
                                              @RequestParam(required = false) String country,
                                              @RequestParam(required = false) String class1,
                                              @RequestParam(required = false) String class2,
                                              @RequestParam(required = false) String type){
        String[] location = new String[]{country, class1, class2};
        Page<User> pageUsers = userService.findUsersByCondition(page-1, size, location, type);
        System.out.println(pageUsers);
        List<UserDto.Response> data = mapper.usersToUsersResponseDtos(pageUsers.getContent());
        return new ResponseEntity(new MultiResponseDto<>(data, pageUsers), HttpStatus.OK);
    }
}
