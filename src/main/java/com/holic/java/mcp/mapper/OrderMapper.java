package com.holic.java.mcp.mapper;

import com.holic.java.mcp.dto.OrderDto;
import com.holic.java.mcp.model.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for OrderEntity <-> OrderDto.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderEntity toEntity(OrderDto dto);

    OrderDto toDto(OrderEntity entity);
}


