/**
 * @program school-bus
 * @description: UserConverter
 * @author: mf
 * @create: 2020/02/26 02:29
 */
package com.stylefeng.guns.rest.modular.user.converter;

import com.stylefeng.guns.rest.common.persistence.model.SbUserT;
import com.stylefeng.guns.rest.user.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mappings({})
    SbUserT vo2SbUserT(UserVo userVo);

}
