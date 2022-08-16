package com.example.projectPrac.v1.user.dto;

import lombok.AllArgsConstructor;

public class UserDto {

    @AllArgsConstructor
    public static class Response{
        private long userId;
        private String name;
        private String location;
        private String companyName;
        private String companyType;
    }
}
