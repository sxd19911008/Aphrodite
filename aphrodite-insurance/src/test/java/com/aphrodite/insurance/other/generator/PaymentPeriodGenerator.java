package com.aphrodite.insurance.other.generator;

import com.aphrodite.common.utils.AphUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentPeriodGenerator extends AbstractGeneratorDecorator {
    private final List<Integer> periods;

    public PaymentPeriodGenerator(Generator wrappedGenerator) {
        super(wrappedGenerator);
        periods = null;
    }

    public PaymentPeriodGenerator(Generator wrappedGenerator, List<Integer> periods) {
        super(wrappedGenerator);
        this.periods = periods;
    }

    @Override
    protected List<List<String>> process(List<List<String>> columnValueListRes) {
        List<List<String>> newCloumnValueList;

        if (AphUtils.isEmpty(periods)) {
            for (List<String> row : columnValueListRes) {
                row.add( "0");
            }
            newCloumnValueList = columnValueListRes;
        } else {
            newCloumnValueList = new ArrayList<>();
            for (List<String> row : columnValueListRes) {
                for (Integer period : periods) {
                    List<String> newRow = new ArrayList<>(row);
                    newRow.add(String.valueOf(period));
                    newCloumnValueList.add(newRow);
                }
            }
        }
        return newCloumnValueList;
    }
}

