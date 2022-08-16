package com.example.projectPrac.v1.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

    @AllArgsConstructor
    @Getter
    public static class Response{
        private long userId;
        private String name;
        private String companyLocation;
        private String companyName;
        private String companyType;
    }
}
