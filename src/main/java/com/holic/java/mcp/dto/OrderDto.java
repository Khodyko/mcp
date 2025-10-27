package com.holic.java.mcp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderDto {
    private Integer id;
    private Integer productId;
    private Integer customerId;
    private Integer quantity;
    private Boolean fulfilled;
}


