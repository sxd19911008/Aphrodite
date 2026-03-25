package com.aphrodite.insurance.component.method;

import com.aphrodite.insurance.component.CacheComponent;
import com.ethan.step.dto.OneOffStepParams;
import com.ethan.step.dto.StepContext;
import com.ethan.step.dto.StepInfo;
import com.ethan.step.intf.JavaStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("queryPremRateTbl")
public class QueryPremRateTbl implements JavaStep {

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public Object invoke(StepInfo stepInfo, StepContext stepContext, OneOffStepParams oneOffStepParams) {
        Map<String, Object> paramsMap = stepContext.getParamsMap();
        return cacheComponent.queryPremRateTbl(paramsMap);
    }
}
