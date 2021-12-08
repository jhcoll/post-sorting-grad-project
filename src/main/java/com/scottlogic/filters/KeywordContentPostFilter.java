package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KeywordContentPostFilter implements PostFilter {
    final String Keyword;

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
        outputList.removeIf(a -> !a.getContents().contains(Keyword));
        return outputList;
    }
}
