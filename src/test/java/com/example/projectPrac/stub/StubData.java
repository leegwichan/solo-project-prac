package com.example.projectPrac.stub;


import com.example.projectPrac.v1.companyLocation.entity.Location;
import com.example.projectPrac.v1.companyType.entity.CompanyType;
import com.example.projectPrac.v1.user.dto.UserDto;
import com.example.projectPrac.v1.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class StubData {
    public static Location getLocation() {
        return new Location(1, "Korea", "서울시", "성북구", null);
    }
    public static CompanyType getCompanyType(){
        return new CompanyType(1,"교육계",null);
    }

    public static Page<User> getMultiUser(){
        User user1 = new User(1L,"이충안","1111",getLocation(),"안회사",getCompanyType());
        User user2 = new User(2L,"삼충안","2222",getLocation(),"삼회사",getCompanyType());
        return new PageImpl<>(List.of(user1, user2),
                PageRequest.of(0,10, Sort.by("userId").descending()), 2);
    }

    public static List<UserDto.Response> getMultiUserResponseDto() {
        UserDto.Response userDto1 = new UserDto.Response(1L, "이충안", "Korea, 서울시 성북구", "안회사","교육계");
        UserDto.Response userDto2 = new UserDto.Response(2L, "삼충안", "Korea, 서울시 성북구", "삼회사","교육계");
        return List.of(userDto1, userDto2);
    }
}
