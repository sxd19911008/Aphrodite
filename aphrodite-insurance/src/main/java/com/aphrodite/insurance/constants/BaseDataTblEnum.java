package com.aphrodite.insurance.constants;

import lombok.Getter;

@Getter
public enum BaseDataTblEnum {

    PREM_RATE_TBL("PREM_RATE_TBL", "费率表"),
    ;


    private final String tableName;
    private final String description;

    BaseDataTblEnum(String tableName, String description) {
        this.tableName = tableName;
        this.description = description;
    }
}
