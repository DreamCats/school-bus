/**
 * @program school-bus-cloud
 * @description: UserConverter
 * @author: mf
 * @create: 2020/10/31 15:34
 */
package com.dream.bus.common.converter;

import com.dream.bus.convert.DateMapper;
import com.dream.bus.model.User;
import com.dream.bus.user.param.UserDto;
import com.dream.bus.user.param.UserRegisterRequest;
import com.dream.bus.user.param.UserUpdateInfoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface UserConverter {

    @Mappings({
            @Mapping(source = "request.userPhone", target = "userPhone"),
            @Mapping(source = "request.id", target = "uuid"),
    })
    User res2User(UserUpdateInfoRequest request);

    @Mappings({
            @Mapping(source = "user.money", target = "money")

    })
    UserDto User2Res(User user);

    @Mappings({
            @Mapping(source = "request.username", target = "userName"),
            @Mapping(source = "request.password", target = "userPwd"),
            @Mapping(source = "request.phone", target = "userPhone"),
    })
    User res2User(UserRegisterRequest request);

}
