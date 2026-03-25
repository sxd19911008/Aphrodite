CREATE TABLE `plan_correlation_tbl`
(
    `plan_code`   varchar(10)  NOT NULL COMMENT '险种代码',
    `table_name`  varchar(50)  NOT NULL COMMENT '表名',
    `correlation` varchar(500) NOT NULL COMMENT '相关性',
    PRIMARY KEY (`plan_code`, table_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='险种基数相关性表';
