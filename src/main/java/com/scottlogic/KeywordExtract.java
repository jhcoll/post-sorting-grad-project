package com.scottlogic;

import java.util.List;
import java.util.Map;

public interface KeywordExtract {
    Map.Entry<String, Integer> extract(String inputContent);
}
