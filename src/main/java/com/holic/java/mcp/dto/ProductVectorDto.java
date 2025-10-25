package com.holic.java.mcp.dto;

import lombok.*;

import java.util.HashMap;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class ProductVectorDto {

    private Long  id;
    private String name;
    private String type;
    private String description;

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("type", type);
        return map;
    }

}
