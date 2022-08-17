package com.example.projectPrac.user.controller;

import com.example.projectPrac.stub.StubData;
import com.example.projectPrac.v1.exception.BusinessLogicException;
import com.example.projectPrac.v1.exception.ExceptionCode;
import com.example.projectPrac.v1.user.controller.UserController;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.example.projectPrac.util.Document.*;
import static com.example.projectPrac.util.Document.responseField;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class UserControllerErrorTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper mapper;

    @Test
    public void getUsersErrorTest() throws Exception{
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
        // queryParams.add("size", String.valueOf(size));

        ResultActions actions = mockMvc.perform(
                get("/v1/users")
                        .params(queryParams)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        missQueryParameter(actions, "get-users-error");
    }

    @Test
    public void getUsersByConditionErrorTest() throws Exception{
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
        //queryParams.add("page", String.valueOf(page));
        queryParams.add("size", String.valueOf(size));
        queryParams.add("class1", class1);
        queryParams.add("type", companyType);

        ResultActions actions = mockMvc.perform(
                get("/v1/users/search")
                        .params(queryParams)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        missQueryParameter(actions, "get-users-search-error");

    }

    private ResultActions missQueryParameter(ResultActions actions, String identifier) throws Exception{
        return actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").isString())
                .andDo(getMethodDocument(identifier,
                List.of(responseField("status", JsonFieldType.NUMBER,"상태 코드"),
                        responseField("message", JsonFieldType.STRING, "에러 메세지"))));
    }

    @Test
    public void getUsersNotFoundTest() throws Exception{
        Page<User> userPage = StubData.getMultiUser();
        int totalPages = userPage.getTotalPages();
        int size = userPage.getSize();
        int page = totalPages / size +1;

        given(userService.findUsers(page-1,size))
                .willThrow(new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
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

        notExistUser(actions, "get-user-notFound");
    }

    @Test
    public void getUsersByConditionNotFoundTest() throws Exception{
        Page<User> userPage = StubData.getMultiUser();
        int totalPages = userPage.getTotalPages();
        int size = userPage.getSize();
        int page = totalPages / size +1;
        String class1 = StubData.getLocation().getClass1();
        String companyType = StubData.getCompanyType().getCompanyType();

        given(userService.findUsersByCondition(anyInt(), anyInt(), any(String[].class), anyString()))
                .willThrow(new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
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

        notExistUser(actions, "get-users-search-notFound");

    }

    private ResultActions notExistUser(ResultActions actions, String identifier) throws Exception{
        return actions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").isString())
                .andDo(getMethodDocument(identifier,
                        List.of(responseField("status", JsonFieldType.NUMBER,"상태 코드"),
                                responseField("message", JsonFieldType.STRING, "에러 메세지"))));
    }
}
