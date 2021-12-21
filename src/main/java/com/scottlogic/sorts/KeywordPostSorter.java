package com.scottlogic.sorts;

import com.scottlogic.PostSorter;
import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import com.scottlogic.filters.KeywordPostFilter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KeywordPostSorter implements PostSorter {
    private final String keyword;
    private final KeywordPostFilter KeywordPostFilter;

    public KeywordPostSorter(KeywordPostFilter KeywordPostFilter) {
        this.KeywordPostFilter = KeywordPostFilter;
        this.keyword = KeywordPostFilter.getKeyword();
    }

    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> filteredList = KeywordPostFilter.filter(inputList);

        Map<UserPost, Integer> wordMap = new HashMap<>();

        for (UserPost post : filteredList) {
            Matcher m = Pattern.compile(keyword).matcher(post.getContents());
            int wordCount = 0;
            while (m.find()) {
                wordCount++;
            }
            wordMap.put(post, wordCount);
        }

        switch (SortOrder) {
            case ASC:
                return wordMap
                        .entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .map(Map.Entry::getKey).collect(Collectors.toList());
            case DESC:
                return wordMap
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey).collect(Collectors.toList());
            default:
                return null;
        }
    }
}
