package com.aphrodite.insurance.pkg.dao;

import com.aphrodite.insurance.pkg.entity.CalcPremiumByPolicyNoJsonPkgEntity;
import com.aphrodite.insurance.pkg.entity.CalcPremiumJsonPkgEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**
 * 保费计算存储过程调用 Mapper
 * 对应 Oracle 包：premium_package
 */
@Mapper
public interface PremiumPkgMapper {

    /**
     * 根据保单号计算保费
     * 调用存储过程：premium_package.calc_premium_by_policy_no_json
     */
    @Select("{call premium_package.calc_premium_by_policy_no_json(" +
            "#{policyNo, mode=IN, jdbcType=VARCHAR}, " +
            "#{calcDate, mode=IN, jdbcType=DATE}, " +
            "#{jsonPremiumMap, mode=OUT, jdbcType=VARCHAR}, " +
            "#{flag, mode=OUT, jdbcType=VARCHAR}, " +
            "#{msg, mode=OUT, jdbcType=VARCHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void calcPremiumByPolicyNoJson(CalcPremiumByPolicyNoJsonPkgEntity entity);

    /**
     * 计算保费，需要传入所有保单信息
     * 调用存储过程：premium_package.calc_premium_json
     */
    @Select("{call premium_package.calc_premium_json(" +
            "#{jsonPolicyInfo, mode=IN, jdbcType=VARCHAR}, " +
            "#{jsonPolicyPlanList, mode=IN, jdbcType=VARCHAR}, " +
            "#{calcDate, mode=IN, jdbcType=DATE}, " +
            "#{jsonPremiumMap, mode=OUT, jdbcType=VARCHAR}, " +
            "#{flag, mode=OUT, jdbcType=VARCHAR}, " +
            "#{msg, mode=OUT, jdbcType=VARCHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void calcPremiumJson(CalcPremiumJsonPkgEntity entity);

}
