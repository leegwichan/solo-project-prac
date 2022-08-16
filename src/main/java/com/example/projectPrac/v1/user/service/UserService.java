package com.example.projectPrac.v1.user.service;

import com.example.projectPrac.v1.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public Page<User> findUsers(int page, int size){
        return null;
    }

    public Page<User> findUsersByCondition(int page, int size, String[] location, String companyType){
        return null;
    }


}
