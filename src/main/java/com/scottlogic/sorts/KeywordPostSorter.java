package com.scottlogic.sorts;

import com.scottlogic.PostSorter;
import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import com.scottlogic.filters.KeywordPostFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        NavigableMap<Integer, UserPost> wordMap = new TreeMap<>();

        if (SortOrder == com.scottlogic.SortOrder.ASC) {
            wordMap = new TreeMap<Integer, UserPost>().descendingMap();
        }

        for (UserPost post : filteredList) {
            Matcher m = Pattern.compile(keyword).matcher(post.getContents());
            int wordCount = 0;
            while (m.find()) {
                wordCount++;
            }
            wordMap.put(wordCount, post);
        }
        return new ArrayList<>(wordMap.values());
    }
}
