/**
 * @program school-bus
 * @description: UserConverter
 * @author: mf
 * @create: 2020/02/26 02:29
 */
package com.stylefeng.guns.rest.modular.user.converter;

import com.stylefeng.guns.rest.common.convert.DateMapper;
import com.stylefeng.guns.rest.common.persistence.model.SbUserT;
import com.stylefeng.guns.rest.user.vo.UserRegisterRequest;
import com.stylefeng.guns.rest.user.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses = DateMapper.class)
public interface UserConverter {

    @Mappings({})
    SbUserT res2SbUserT(UserRegisterRequest request);

    @Mappings({
    })
    UserVo sbUserT2Res(SbUserT sbUserT);

}
