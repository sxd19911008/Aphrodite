package com.aphrodite.insurance.other.generator;

import java.util.List;

public abstract class AbstractGeneratorDecorator implements Generator {
    protected Generator wrappedGenerator;

    public AbstractGeneratorDecorator(Generator wrappedGenerator) {
        this.wrappedGenerator = wrappedGenerator;
    }

    @Override
    public List<List<String>> generate(List<List<String>> columnValueListRes) {
        List<List<String>> newCloumnValueList = wrappedGenerator.generate(columnValueListRes);
        return this.process(newCloumnValueList);
    }

    protected abstract List<List<String>> process(List<List<String>> columnValueListRes);
}

