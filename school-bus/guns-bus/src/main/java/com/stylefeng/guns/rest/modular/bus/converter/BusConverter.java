/**
 * @program school-bus
 * @description: BusConverter
 * @author: mf
 * @create: 2020/03/01 16:27
 */
package com.stylefeng.guns.rest.modular.bus.converter;

import com.stylefeng.guns.rest.bus.dto.BusDto;
import com.stylefeng.guns.rest.common.convert.DateMapper;
import com.stylefeng.guns.rest.common.persistence.model.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface BusConverter {

    @Mappings({})
    List<BusDto> bus2List(List<Bus> buses);
}
