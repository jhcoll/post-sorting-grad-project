package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class KeywordPostFilter implements PostFilter {
    private final String Keyword;

    public String getKeyword() {
        return Keyword;
    }

    public KeywordPostFilter(String keywordInput) {
        if (keywordInput == null) {
            throw new IllegalArgumentException("keyword input cannot be null");
        }
        this.Keyword = keywordInput.toLowerCase();
    }

    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        if (Objects.equals(Keyword, "")) {
            return new ArrayList<>();
        }
        List<UserPost> outputList = new ArrayList<>(inputList);
        return outputList
                .stream()
                .filter(a -> a.getContents().toLowerCase().contains(Keyword))
                .collect(Collectors.toList());
    }
}
