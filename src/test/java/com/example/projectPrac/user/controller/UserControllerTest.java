package com.example.projectPrac.user.controller;

import com.example.projectPrac.v1.user.controller.UserController;
import com.example.projectPrac.v1.user.dto.UserDto;
import com.example.projectPrac.v1.user.mapper.UserMapper;
import com.example.projectPrac.v1.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper mapper;

    @Test
    public void getUsersTest(){
        int page = 1, size = 10;
        UserDto.Response response = new UserDto.Response(1, "이충안", "Korea, 서울시 성북구",
                "안회사", "교육계");


        given(userService.findUsers(page-1,size)).willReturn();
        given(mapper.usersToUsersResponseDtos(Mockito.any())).willReturn();
    }
}
