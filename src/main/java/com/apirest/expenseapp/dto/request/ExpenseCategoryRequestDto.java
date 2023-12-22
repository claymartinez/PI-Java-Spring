package com.apirest.expenseapp.dto.request;

import lombok.Data;

public class ExpenseCategoryRequestDto {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
