/**
 * @program school-bus
 * @description: CountConverter
 * @author: mf
 * @create: 2020/03/02 13:00
 */

package com.stylefeng.guns.rest.modular.bus.converter;

import com.stylefeng.guns.rest.bus.dto.CountDto;
import com.stylefeng.guns.rest.common.convert.DateMapper;
import com.stylefeng.guns.rest.common.persistence.model.Count;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface CountConverter {

    @Mappings({})
    List<CountDto> count2Res(List<Count> counts);
}
