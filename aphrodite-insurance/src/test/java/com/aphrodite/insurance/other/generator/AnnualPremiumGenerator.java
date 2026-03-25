package com.aphrodite.insurance.other.generator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AnnualPremiumGenerator extends AbstractGeneratorDecorator {
    private final Integer minPremium;
    private final Integer maxPremium;

    public AnnualPremiumGenerator(Generator wrappedGenerator) {
        super(wrappedGenerator);
        this.maxPremium = null;
        this.minPremium = null;
    }

    public AnnualPremiumGenerator(Generator wrappedGenerator, int minPremium, int maxPremium) {
        super(wrappedGenerator);
        this.minPremium = minPremium * 100;
        this.maxPremium = maxPremium * 100;
    }

    @Override
    protected List<List<String>> process(List<List<String>> columnValueListRes) {
        if (this.minPremium == null || this.maxPremium == null) {
            for (List<String> list : columnValueListRes) {
                list.add( "0");
            }
        } else {
            for (List<String> row : columnValueListRes) {
                double randomValue = (double) ThreadLocalRandom.current().nextInt(this.minPremium, this.maxPremium) / 100;
                row.add(String.valueOf(randomValue));
            }
        }
        return columnValueListRes;
    }
}

