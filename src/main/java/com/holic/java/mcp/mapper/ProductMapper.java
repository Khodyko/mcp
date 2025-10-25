package com.holic.java.mcp.mapper;

import com.holic.java.mcp.dto.ProductDto;
import com.holic.java.mcp.dto.ProductVectorDto;
import com.holic.java.mcp.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct mapper for Product <-> ProductDto.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto dto);
    List<Product> toEntity(List<ProductDto> dtos);

    ProductDto toDto(Product entity);
    List<ProductDto> toDtos(List<Product> entities);

    ProductVectorDto toVectorDto(Product entity);
    List<ProductVectorDto> toVectorDtos(List<Product> entities);
}


