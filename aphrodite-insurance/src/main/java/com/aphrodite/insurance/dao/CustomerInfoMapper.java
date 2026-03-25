package com.aphrodite.insurance.dao;

import com.aphrodite.insurance.entity.CustomerInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * CUSTOMER_INFO 客户信息表 Mapper 接口
 */
@Mapper
public interface CustomerInfoMapper extends BaseMapper<CustomerInfoEntity> {
}

