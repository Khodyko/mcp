package com.holic.java.mcp.enums;

public enum ToolEnum {
    PRODUCT("Заказ продукта пользователем", 1),
    DB_LOADER("Настройка базы данных", 2),
    NOTHING("Настройка базы данных", 3);

    ToolEnum(String description, Integer number) {
        this.description = description;
        this.number = number;
    }

    private String description;
    private Integer number;
    public String getDescription() {
        return description;
    }

    public Integer getNumber() {
        return number;
    }
}
