package com.example.projectPrac.user.controller;

import com.example.projectPrac.stub.StubData;
import com.example.projectPrac.v1.companyLocation.entity.Location;
import com.example.projectPrac.v1.user.controller.UserController;
import com.example.projectPrac.v1.user.dto.UserDto;
import com.example.projectPrac.v1.user.entity.User;
import com.example.projectPrac.v1.user.mapper.UserMapper;
import com.example.projectPrac.v1.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.projectPrac.util.Document.*;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
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
    public void getUsersTest() throws Exception{
        Page<User> userPage = StubData.getMultiUser();
        int totalPages = userPage.getTotalPages();
        int size = userPage.getSize();
        int page = totalPages / size +1;

        given(userService.findUsers(page-1,size))
                .willReturn(userPage);
        given(mapper.usersToUsersResponseDtos(any()))
                .willReturn(StubData.getMultiUserResponseDto());

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", String.valueOf(page));
        queryParams.add("size", String.valueOf(size));

        ResultActions actions = mockMvc.perform(
                get("/v1/users")
                        .params(queryParams)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        ResultActions testActions =
                actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.pageInfo.page").value(page))
                .andExpect(jsonPath("$.pageInfo.size").value(size));

        testActions
                .andDo(getMethodDocument("get-users",
                        List.of(queryParam("page","page ??????"),
                                queryParam("size","page ??????")),
                        List.of(responseField("data", JsonFieldType.ARRAY, "?????? ?????????"),
                                responseField("data[].userId", JsonFieldType.NUMBER, "?????? ?????????"),
                                responseField("data[].name",JsonFieldType.STRING, "?????? ??????"),
                                responseField("data[].companyLocation", JsonFieldType.STRING, "?????? ??????"),
                                responseField("data[].companyName", JsonFieldType.STRING, "?????? ??????"),
                                responseField("data[].companyType", JsonFieldType.STRING, "?????? ??????"),
                                responseField("pageInfo.page", JsonFieldType.NUMBER, "?????? ?????????"),
                                responseField("pageInfo.size", JsonFieldType.NUMBER, "?????? ????????? ??????"),
                                responseField("pageInfo.totalElements", JsonFieldType.NUMBER, "??? User ???"),
                                responseField("pageInfo.totalPages", JsonFieldType.NUMBER, "??? ????????? ???"))));
    }

    @Test
    public void getUsersByConditionTest() throws Exception{
        Page<User> userPage = StubData.getMultiUser();
        int totalPages = userPage.getTotalPages();
        int size = userPage.getSize();
        int page = totalPages / size +1;
        String class1 = StubData.getLocation().getClass1();
        String companyType = StubData.getCompanyType().getCompanyType();

        given(userService.findUsersByCondition(anyInt(), anyInt(), any(String[].class), anyString()))
                .willReturn(StubData.getMultiUser());
        given(mapper.usersToUsersResponseDtos(Mockito.any()))
                .willReturn(StubData.getMultiUserResponseDto());

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", String.valueOf(page));
        queryParams.add("size", String.valueOf(size));
        queryParams.add("class1", class1);
        queryParams.add("type", companyType);

        ResultActions actions = mockMvc.perform(
                get("/v1/users/search")
                        .params(queryParams)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        ResultActions testActions =
                actions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.data").isArray())
                        .andExpect(jsonPath("$.pageInfo.page").value(page))
                        .andExpect(jsonPath("$.pageInfo.size").value(size));

        testActions
                .andDo(getMethodDocument("get-users-search",
                        List.of(queryParam("page","page ??????"),
                                queryParam("size","page ??????"),
                                queryParam("country","?????? ?????? : ??????").optional(),
                                queryParam("class1","?????? ?????? : ?????????1").optional(),
                                queryParam("class2","?????? ?????? : ?????????2").optional(),
                                queryParam("type","?????? ??????").optional()),
                        List.of(responseField("data", JsonFieldType.ARRAY, "?????? ?????????"),
                                responseField("data[].userId", JsonFieldType.NUMBER, "?????? ?????????"),
                                responseField("data[].name",JsonFieldType.STRING, "?????? ??????"),
                                responseField("data[].companyLocation", JsonFieldType.STRING, "?????? ??????"),
                                responseField("data[].companyName", JsonFieldType.STRING, "?????? ??????"),
                                responseField("data[].companyType", JsonFieldType.STRING, "?????? ??????"),
                                responseField("pageInfo.page", JsonFieldType.NUMBER, "?????? ?????????"),
                                responseField("pageInfo.size", JsonFieldType.NUMBER, "?????? ????????? ??????"),
                                responseField("pageInfo.totalElements", JsonFieldType.NUMBER, "??? User ???"),
                                responseField("pageInfo.totalPages", JsonFieldType.NUMBER, "??? ????????? ???"))));

    }
}
