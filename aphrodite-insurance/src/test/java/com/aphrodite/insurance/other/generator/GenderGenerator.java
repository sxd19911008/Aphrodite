package com.aphrodite.insurance.other.generator;

import java.util.*;

public class GenderGenerator extends AbstractGeneratorDecorator {
    private final List<String> genderList = new ArrayList<>(Arrays.asList("M", "F"));
    private final boolean isRelated;

    public GenderGenerator(Generator wrappedGenerator) {
        super(wrappedGenerator);
        this.isRelated = false;
    }

    public GenderGenerator(Generator wrappedGenerator, boolean isRelated) {
        super(wrappedGenerator);
        this.isRelated = isRelated;
    }

    @Override
    protected List<List<String>> process(List<List<String>> columnValueListRes) {
        List<List<String>> newCloumnValueList;
        if (this.isRelated) {
            newCloumnValueList = new ArrayList<>();
            for (List<String> row : columnValueListRes) {
                for (String gender : this.genderList) {
                    List<String> newRow = new ArrayList<>(row);
                    newRow.add("'" + gender + "'");
                    newCloumnValueList.add(newRow);
                }
            }
        } else {
            for (List<String> row : columnValueListRes) {
                row.add( "'0'");
            }
            newCloumnValueList = columnValueListRes;
        }
        return newCloumnValueList;
    }
}

