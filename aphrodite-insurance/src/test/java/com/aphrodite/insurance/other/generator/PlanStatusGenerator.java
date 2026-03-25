package com.aphrodite.insurance.other.generator;

import com.aphrodite.common.utils.AphUtils;

import java.util.ArrayList;
import java.util.List;

public class PlanStatusGenerator extends AbstractGeneratorDecorator {
    private final List<String> statusList;

    public PlanStatusGenerator(Generator wrappedGenerator) {
        super(wrappedGenerator);
        statusList = null;
    }

    public PlanStatusGenerator(Generator wrappedGenerator, List<String> statusList) {
        super(wrappedGenerator);
        this.statusList = statusList;
    }

    @Override
    protected List<List<String>> process(List<List<String>> columnValueListRes) {
        List<List<String>> newCloumnValueList;

        if (AphUtils.isEmpty(statusList)) {
            for (List<String> row : columnValueListRes) {
                row.add( "'0'");
            }
            newCloumnValueList = columnValueListRes;
        } else {
            newCloumnValueList = new ArrayList<>();
            for (List<String> row : columnValueListRes) {
                for (String status : statusList) {
                    List<String> newRow = new ArrayList<>(row);
                    newRow.add("'" + status + "'");
                    newCloumnValueList.add(newRow);
                }
            }
        }
        return newCloumnValueList;
    }
}

