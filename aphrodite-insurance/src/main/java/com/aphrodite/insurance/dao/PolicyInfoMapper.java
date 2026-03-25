package com.aphrodite.insurance.dao;

import com.aphrodite.insurance.entity.PolicyInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * POLICY_INFO 保单主信息表 Mapper 接口
 */
@Mapper
public interface PolicyInfoMapper extends BaseMapper<PolicyInfoEntity> {
}

