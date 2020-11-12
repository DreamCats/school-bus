/**
 * @program school-bus-cloud
 * @description: BusMapper
 * @author: mf
 * @create: 2020/11/08 13:16
 */
package com.dream.bus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dream.bus.model.Bus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusMapper extends BaseMapper<Bus> {
}
