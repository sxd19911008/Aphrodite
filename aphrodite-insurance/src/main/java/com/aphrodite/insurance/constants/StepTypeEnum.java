package com.aphrodite.insurance.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum StepTypeEnum {

    CM001("CM001", "公共逻辑处理"),
    PR001("PR001", "保费基础计算"),
    BN001("BN001", "保险收益基础计算"),
    CV001("CV001", "现金价值基础计算"),
    ;
    private final String code;
    private final String name;

    StepTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public List<String> getStepTypeList() {
        return Arrays.stream(StepTypeEnum.values()).map(StepTypeEnum::getCode).toList();
    }
}
