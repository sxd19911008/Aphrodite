package com.aphrodite.insurance.other.generator;

import java.util.ArrayList;
import java.util.List;

public class CoveragePeriodGenerator extends AbstractGeneratorDecorator {
    private final Integer startPeriod;
    private final Integer endPeriod;

    public CoveragePeriodGenerator(Generator wrappedGenerator) {
        super(wrappedGenerator);
        startPeriod = null;
        endPeriod = null;
    }

    public CoveragePeriodGenerator(Generator wrappedGenerator, int startPeriod, int endPeriod) {
        super(wrappedGenerator);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    @Override
    protected List<List<String>> process(List<List<String>> columnValueListRes) {
        List<List<String>> newCloumnValueList;
        if (startPeriod == null || endPeriod == null) {
            for (List<String> row : columnValueListRes) {
                row.add( "0");
            }
            newCloumnValueList = columnValueListRes;
        } else {
            newCloumnValueList = new ArrayList<>();
            for (List<String> row : columnValueListRes) {
                for (int period = startPeriod; period <= endPeriod; period++) {
                    List<String> newRow = new ArrayList<>(row);
                    newRow.add(String.valueOf(period));
                    newCloumnValueList.add(newRow);
                }
            }
        }
        return newCloumnValueList;
    }
}

