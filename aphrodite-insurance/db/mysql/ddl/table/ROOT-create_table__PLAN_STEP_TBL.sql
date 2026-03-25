CREATE TABLE `plan_step_tbl`
(
    `plan_code`  varchar(10) NOT NULL COMMENT '险种代码',
    `step_type`  varchar(10) NOT NULL COMMENT '步骤类型',
    `seq`        tinyint     NOT NULL COMMENT '顺序',
    `step_code`  varchar(20) NOT NULL COMMENT '步骤标识',
    `created_by` varchar(20) DEFAULT (CURRENT_USER()) COMMENT '创建人',
    `created_at` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` varchar(20) DEFAULT (CURRENT_USER()) COMMENT '更新人',
    `updated_at` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`plan_code`, `step_type`, `seq`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='险种步骤关联表';