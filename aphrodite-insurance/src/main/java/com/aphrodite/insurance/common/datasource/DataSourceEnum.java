package com.aphrodite.insurance.common.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceEnum {

    MySQL("mysql"),
    Oracle("oracle");

    private final String db;
}
