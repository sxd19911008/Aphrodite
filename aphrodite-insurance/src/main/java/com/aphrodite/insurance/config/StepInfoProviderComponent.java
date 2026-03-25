package com.aphrodite.insurance.config;

import com.aphrodite.common.utils.AphUtils;
import com.aphrodite.common.utils.JsonUtils;
import com.aphrodite.insurance.common.datasource.DataSourceContextHolder;
import com.aphrodite.insurance.common.datasource.DataSourceEnum;
import com.aphrodite.insurance.dao.StepTblMapper;
import com.aphrodite.insurance.dto.StepContentTemp;
import com.aphrodite.insurance.entity.StepTblEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.step.dto.StepContent;
import com.ethan.step.dto.StepInfo;
import com.ethan.step.intf.StepInfoProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StepInfoProviderComponent implements StepInfoProvider {

    @Autowired
    private StepTblMapper stepTblMapper;

    @Override
    public List<StepInfo> loadStepInfoList() {
        /* 切换数据源 */
        DataSourceContextHolder.setDataSource(DataSourceEnum.MySQL.getDb());
        /* 查询 step 信息 */
        List<StepTblEntity> stepTblList = stepTblMapper.selectList(new LambdaQueryWrapper<StepTblEntity>()
                .select(StepTblEntity::getStepCode,
                        StepTblEntity::getStepName,
                        StepTblEntity::getStepType,
                        StepTblEntity::getContentType,
                        StepTblEntity::getContent,
                        StepTblEntity::getParams,
                        StepTblEntity::getReturnFields
                )
        );

        List<StepInfo> stepInfoList = new ArrayList<>();

        /* 组装 step 对象 */
        if (AphUtils.isNotEmpty(stepTblList)) {
            for (StepTblEntity entity : stepTblList) {
                // 解析 step 正文对象
                StepContent content = null;
                if (AphUtils.isNotBlank(entity.getContent())) {
                    StepContentTemp contentTemp = JsonUtils.readValue(entity.getContent(), StepContentTemp.class);
                    content = StepContent.builder()
                            .constant(contentTemp.getConstant())
                            .constantType(contentTemp.getConstantType())
                            .javaMethod(contentTemp.getJavaMethod())
                            .stepList(contentTemp.getStepList())
                            .expression(contentTemp.getExpression())
                            .returnType(contentTemp.getReturnType())
                            .build();
                }
                // 解析 step 参数名称列表
                List<String> paramNameList = null;
                if (AphUtils.isNotBlank(entity.getParams())) {
                    paramNameList = JsonUtils.readValue(entity.getParams(), new TypeReference<ArrayList<String>>() {
                    });
                }
                // 解析 step 返回字段
                List<String> returnFieldList = null;
                if (AphUtils.isNotBlank(entity.getReturnFields())) {
                    returnFieldList = JsonUtils.readValue(entity.getReturnFields(), new TypeReference<ArrayList<String>>() {
                    });
                }
                // 组装 step 信息对象
                stepInfoList.add(StepInfo.builder()
                        .stepCode(entity.getStepCode())
                        .stepName(entity.getStepName())
                        .stepType(entity.getStepType())
                        .contentType(entity.getContentType())
                        .content(content)
                        .paramNameList(paramNameList)
                        .returnFieldList(returnFieldList)
                        .build());
            }
        }

        return stepInfoList;
    }
}
