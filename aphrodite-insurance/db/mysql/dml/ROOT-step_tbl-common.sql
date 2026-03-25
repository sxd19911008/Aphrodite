DELETE FROM step_tbl WHERE step_code IN ('CM00100001', 'CM00100002', 'CM00100003', 'PR00200001', 'PR00100001');

-- 计算周岁年龄 公共步骤
INSERT INTO `step_tbl` (`step_code`, `step_name`, `step_type`, `summary`, `content_type`, `content`, `params`, `return_fields`, `description`)
VALUES ('CM00100001', 'CM001_fullAge', 'CM001', '周岁年龄', 'engine',
        '{"expression": "Utils.yearsBetween(birthDate, calcDate, ''L'')"}',
        '["birthDate", "calcDate"]', null, '周岁年龄=yearsBetween(出生日期,计算日期,L)');

-- 计算保费型险种的份数 公共步骤
INSERT INTO `step_tbl` (`step_code`, `step_name`, `step_type`, `summary`, `content_type`, `content`, `params`, `return_fields`, `description`)
VALUES ('CM00100002', 'CM001_units', 'CM001', '保额型险种的份数', 'engine',
        '{"expression": "sumAssured / unitPrice"}',
        '["sumAssured", "unitPrice"]', null, '保额型险种的份数=保费/1份价格');

-- 查询费率表 公共步骤
INSERT INTO `step_tbl` (`step_code`, `step_name`, `step_type`, `summary`, `content_type`, `content`, `params`, `return_fields`, `description`)
VALUES ('CM00100003', 'premRateTbl', 'CM001', '费率表信息', 'java',
        '{"javaMethod": "queryPremRateTbl"}',
        null, null, '根据险种相关性查询费率表信息');


INSERT INTO `step_tbl` (`step_code`, `step_name`, `step_type`, `summary`, `content_type`, `content`, `params`, `return_fields`, `description`)
VALUES ('PR00200001', 'PR002_premiumAmount', 'PR002', '保费', 'engine',
        '{"expression": "premRateTbl.annualPremium * CM001_units"}',
        '["premRateTbl", "CM001_units"]', null, '保费=年交保费*份数');

INSERT INTO `step_tbl` (`step_code`, `step_name`, `step_type`, `summary`, `content_type`, `content`, `params`, `return_fields`, `description`)
VALUES ('PR00100001', 'PR001_100', 'PR002', '保费', 'composite',
'{"stepList": {
"1": [{"stepCode":"CM00100001","paramNameMap":{"birthDate":"insuredPerson.birthDate"},"resultFieldMap":{"CM001_fullAge":"CM001_insAge"}},
      {"stepCode":"CM00100002","paramNameMap":{"sumAssured":"policyPlan.sumAssured","unitPrice":"plan.unitPrice"}}],
"2": {"stepCode":"CM00100003"},
"3": {"stepCode":"PR00200001"}
}}', null, '["PR002_premiumAmount"]', '保费=年交保费*份数');