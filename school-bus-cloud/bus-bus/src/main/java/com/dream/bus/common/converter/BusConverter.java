/**
 * @program school-bus-cloud
 * @description: BusConverter
 * @author: mf
 * @create: 2020/11/08 13:32
 */
package com.dream.bus.common.converter;

import com.dream.bus.bus.param.BusDto;
import com.dream.bus.convert.DateMapper;
import com.dream.bus.model.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface BusConverter {

    @Mappings({})
    List<BusDto> bus2List(List<Bus> buses);
}
