/**
 * @program school-bus-cloud
 * @description: UserMapper
 * @author: mf
 * @create: 2020/10/31 15:13
 */

package com.dream.bus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dream.bus.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
