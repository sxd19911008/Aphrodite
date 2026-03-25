DELETE FROM plan_correlation_tbl WHERE plan_code = '100';

INSERT INTO `plan_correlation_tbl` (`plan_code`, `table_name`, `correlation`)
VALUES ('100', 'PREM_RATE_TBL', 'gender,age,payment_period,coverage_period');


DELETE FROM plan_step_tbl WHERE plan_code = '100' AND step_type = 'PR001';

INSERT INTO `plan_step_tbl` (`plan_code`, `step_type`, `seq`, `step_code`)
VALUES ('100', 'PR001', 1, 'PR00100001');
