package com.aphrodite.insurance.dao;

import com.aphrodite.insurance.entity.PolicyPlanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * POLICY_PLAN 保单险种明细表 Mapper 接口
 */
@Mapper
public interface PolicyPlanMapper extends BaseMapper<PolicyPlanEntity> {
}

