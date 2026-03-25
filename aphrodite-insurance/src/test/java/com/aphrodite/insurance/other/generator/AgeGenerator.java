package com.aphrodite.insurance.other.generator;

import java.util.ArrayList;
import java.util.List;

public class AgeGenerator extends AbstractGeneratorDecorator {
    private final Integer minAge;
    private final Integer maxAge;

    public AgeGenerator(Generator wrappedGenerator) {
        super(wrappedGenerator);
        minAge = null;
        maxAge = null;
    }

    public AgeGenerator(Generator wrappedGenerator, int minAge, int maxAge) {
        super(wrappedGenerator);
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    protected List<List<String>> process(List<List<String>> columnValueListRes) {
        List<List<String>> newCloumnValueList;
        if (minAge == null || maxAge == null) {
            for (List<String> row : columnValueListRes) {
                row.add( "0");
            }
            newCloumnValueList = columnValueListRes;
        } else {
            newCloumnValueList = new ArrayList<>();
            for (List<String> row : columnValueListRes) {
                for (int age = minAge; age <= maxAge; age++) {
                    List<String> newRow = new ArrayList<>(row);
                    newRow.add(String.valueOf(age));
                    newCloumnValueList.add(newRow);
                }
            }
        }
        return newCloumnValueList;
    }
}

