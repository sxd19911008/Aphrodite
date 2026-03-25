package com.aphrodite.insurance.dao;

import com.aphrodite.insurance.entity.PlanStepTblEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * plan_step_tbl 险种步骤关联表 Mapper 接口
 */
@Mapper
public interface PlanStepTblMapper extends BaseMapper<PlanStepTblEntity> {
}

