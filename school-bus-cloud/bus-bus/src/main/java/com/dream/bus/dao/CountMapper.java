/**
 * @program school-bus-cloud
 * @description: CountMapper
 * @author: mf
 * @create: 2020/11/08 13:17
 */
package com.dream.bus.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dream.bus.bus.param.CountDetailDto;
import com.dream.bus.bus.param.CountSimpleDto;
import com.dream.bus.model.Count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CountMapper extends BaseMapper<Count> {
    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CountSimpleDto> selectCounts(IPage<CountSimpleDto> page, @Param(Constants.WRAPPER) Wrapper<CountSimpleDto> wrapper);

    /**
     *
     * @param wrapper
     * @return
     */
    CountDetailDto selectCountDetailById(@Param(Constants.WRAPPER) Wrapper<CountDetailDto> wrapper);
}
