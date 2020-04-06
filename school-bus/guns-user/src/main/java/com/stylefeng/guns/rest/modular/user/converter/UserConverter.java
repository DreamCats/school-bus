/**
 * @program school-bus
 * @description: UserConverter
 * @author: mf
 * @create: 2020/02/26 02:29
 */
package com.stylefeng.guns.rest.modular.user.converter;


import com.stylefeng.guns.rest.common.convert.DateMapper;
import com.stylefeng.guns.rest.common.persistence.model.User;
import com.stylefeng.guns.rest.user.dto.UserDto;
import com.stylefeng.guns.rest.user.dto.UserRegisterRequest;
import com.stylefeng.guns.rest.user.dto.UserUpdateInfoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface UserConverter {

    @Mappings({
            @Mapping(source = "request.username", target = "userName"),
            @Mapping(source = "request.password", target = "userPwd"),
            @Mapping(source = "request.phone", target = "userPhone")
    })
    User res2User(UserRegisterRequest request);

    @Mappings({
            @Mapping(source = "user.money", target = "money")

    })
    UserDto User2Res(User user);

    @Mappings({
            @Mapping(source = "request.id", target = "uuid"),
    })
    User res2User(UserUpdateInfoRequest request);

}
