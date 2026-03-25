package com.aphrodite.insurance.common.datasource;

import java.util.HashSet;
import java.util.Set;

public class DataSourceContextHolder {

    private static final ThreadLocal<String> dataSourceLocal = new ThreadLocal<>();

    private static final Set<String> dataSourceSet = new HashSet<>();

    /**
     * 初始化 dataSourceSet
     */
    public static void initDataSourceSet(Set<String> dataSourceList) {
        dataSourceSet.addAll(dataSourceList);
    }

    public static String getDataSource() {
        return dataSourceLocal.get();
    }

    public static void setDataSource(String dataSource) {
        if (dataSource == null || dataSource.isBlank()) {
            throw new IllegalArgumentException("dataSource can't be null!");
        }
        if (!dataSourceSet.contains(dataSource)) {
            throw new IllegalArgumentException("【" + dataSource + "】is not configured!");
        }
        dataSourceLocal.set(dataSource);
    }

    public static void removeDataSource() {
        dataSourceLocal.remove();
    }
}
