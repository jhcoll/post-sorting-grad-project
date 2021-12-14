package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class KeywordContentPostFilter implements PostFilter {
    private final String Keyword;

    public KeywordContentPostFilter(String keywordInput) {
        Keyword = keywordInput;
    }

    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        if(Objects.equals(Keyword, "")){return new ArrayList<>();}
        List<UserPost> outputList = new ArrayList<>(inputList);
        outputList.removeIf(a -> !a.getContents().toLowerCase().contains(Keyword.toLowerCase()));
        return outputList;
    }
}
