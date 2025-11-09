package com.holic.java.mcp.dto;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.ai.document.Document;

import java.util.HashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldNameConstants
public class ProductVectorDto {

    private Integer id;
    private String name;
    private String type;
    private String description;

    public ProductVectorDto(Document document) {
        Map<String, Object> meta = document.getMetadata();

        id = (Integer) meta.get(Fields.id);
        name = (String) meta.get(Fields.name);
        type = (String) meta.get(Fields.type);
        description = document.getFormattedContent();
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Fields.id, id);
        map.put(Fields.name, name);
        map.put(Fields.type, type);
        return map;
    }

}
