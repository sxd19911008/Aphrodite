package com.aphrodite.insurance.other.generator;

import java.util.List;

public interface Generator {
    List<List<String>> generate(List<List<String>> columnValueListRes);
}

