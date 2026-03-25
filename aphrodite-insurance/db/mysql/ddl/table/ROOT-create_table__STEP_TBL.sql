CREATE TABLE `step_tbl`
(
    `step_code`     varchar(20)   NOT NULL COMMENT '步骤标识',
    `step_name`     varchar(20)   NOT NULL COMMENT '步骤名称',
    `step_type`     varchar(10)   NOT NULL COMMENT '步骤类型',
    `summary`       varchar(1000) NOT NULL COMMENT '简介',
    `content_type`  varchar(20)   NOT NULL COMMENT '内容类型(详情见StepContentTypeEnum)',
    `content`       json          NOT NULL COMMENT '步骤内容',
    `params`        json          DEFAULT NULL COMMENT '参数，jexl或javaMethod类型可以有参数',
    `return_fields` json          DEFAULT NULL COMMENT '返回字段。多个返回字段配置在这里，否则为空',
    `description`   varchar(4000) DEFAULT NULL COMMENT '步骤介绍',
    `created_by`    varchar(20)   DEFAULT (CURRENT_USER()) COMMENT '创建人',
    `created_at`    datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`    varchar(20)   DEFAULT (CURRENT_USER()) COMMENT '更新人',
    `updated_at`    datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`step_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='步骤表';