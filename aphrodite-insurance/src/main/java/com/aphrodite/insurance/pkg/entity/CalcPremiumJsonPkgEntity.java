package com.aphrodite.insurance.pkg.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 计算保费存储过程参数对象（JSON接口）
 * 对应存储过程：premium_package.calc_premium_json
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "计算保费存储过程参数对象")
public class CalcPremiumJsonPkgEntity {

    @Schema(title = "保单主信息 JSON", description = "输入参数：P_JSON_POLICY_INFO")
    private String jsonPolicyInfo;

    @Schema(title = "险种列表 JSON", description = "输入参数：P_JSON_POLICY_PLAN_LIST")
    private String jsonPolicyPlanList;

    @Schema(title = "计算日期", description = "输入参数：P_CALC_DATE")
    private LocalDateTime calcDate;

    @Schema(title = "保费结果 JSON", description = "输出参数：P_JSON_PREMIUM_MAP")
    private String jsonPremiumMap;

    @Schema(title = "执行状态标志", description = "输出参数：P_FLAG (0:成功, -1:失败)")
    private String flag;

    @Schema(title = "错误信息", description = "输出参数：P_MSG")
    private String msg;
}

