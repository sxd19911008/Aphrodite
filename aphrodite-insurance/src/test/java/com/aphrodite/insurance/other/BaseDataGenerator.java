package com.aphrodite.insurance.other;

import com.aphrodite.common.utils.FileUtils;
import com.aphrodite.insurance.other.generator.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Disabled
public class BaseDataGenerator {

    @Test
    public void generatePremRateTbl() {
        String planCode = "101";
        String filePath = String.format("db/oracle/dml/baseData/INSURANCE_USER-PREM_RATE_TBL-%s.sql", planCode);
        List<List<String>> cloumnValueList = new ArrayList<>();
        // 输入险种代码
        cloumnValueList.add(new ArrayList<>(List.of(planCode)));

        Generator generator = new BaseGenerator();
        generator = new GenderGenerator(generator, true);
        generator = new AgeGenerator(generator, 28, 55);
        generator = new PaymentPeriodGenerator(generator, Arrays.asList(5, 10, 15));
        generator = new CoveragePeriodGenerator(generator, 15, 20);
        generator = new PlanStatusGenerator(generator);
        generator = new AnnualPremiumGenerator(generator, 1000, 5000);
        List<List<String>> columnValueListRes = generator.generate(cloumnValueList);

        List<String> fileDataList = new ArrayList<>();
        fileDataList.add(String.format("DELETE FROM PREM_RATE_TBL WHERE plan_code = '%s';", planCode));
        for (List<String> row : columnValueListRes) {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO PREM_RATE_TBL (PLAN_CODE, GENDER, AGE, PAYMENT_PERIOD, COVERAGE_PERIOD, PLAN_STATUS, ANNUAL_PREMIUM)\nVALUES (");
            for (String columnValue : row) {
                sql.append(columnValue).append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(");");
            fileDataList.add(sql.toString());
        }
        FileUtils.writeLinesToFile(fileDataList, filePath);
    }
}
