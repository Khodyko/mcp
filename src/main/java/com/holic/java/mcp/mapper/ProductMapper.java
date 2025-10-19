package com.holic.java.mcp.mapper;

import com.holic.java.mcp.dto.ProductDto;
import com.holic.java.mcp.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for Product <-> ProductDto.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductDto dto);

    ProductDto toDto(Product entity);
}


