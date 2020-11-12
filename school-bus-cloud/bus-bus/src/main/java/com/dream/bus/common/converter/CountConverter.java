/**
 * @program school-bus
 * @description: CountConverter
 * @author: mf
 * @create: 2020/03/02 13:00
 */

package com.dream.bus.common.converter;

import com.dream.bus.bus.param.CountDto;
import com.dream.bus.convert.DateMapper;
import com.dream.bus.model.Count;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface CountConverter {

    @Mappings({})
    List<CountDto> count2Res(List<Count> counts);
}
