package com.scottlogic.sorts;

import com.scottlogic.KeywordExtract;
import com.scottlogic.UserPost;

import java.util.*;

public class KeywordExtractor implements KeywordExtract {
    @Override
    public Map.Entry<String, Integer> extract(String inputContent) {
        if(inputContent == null){return null;}
        if(inputContent.equals("")){return Map.entry("", 0);}

        String[] userWords = inputContent.replaceAll("[^\\w ]", "").toLowerCase().split(" ");
        NavigableMap<String, Integer> wordMap = new TreeMap<>();

        for (String word : userWords) {
            Integer wordCount = wordMap.get(word);
            if(wordCount == null) {
                wordCount = 1;
            } else {
                wordCount++;
            }
            wordMap.put(word, wordCount);
        }

        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        return maxEntry;
    }
}
