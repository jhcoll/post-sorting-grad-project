package com.scottlogic.sorts;

import com.scottlogic.PostSorter;
import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;

import java.util.*;

public class TopicPostSorter implements PostSorter {

    private final Map<String, List<UserPost>> topicMap = new HashMap<>();

    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> userPostList = new ArrayList<>(inputList);
        List<UserPost> outputList = new ArrayList<>();
        for (UserPost userPost : userPostList) {
            Map<String, Integer> keywordMap = getKeywords(userPost.getContents());
            addToList(keywordMap, userPost);
        }
        for (Map.Entry<String, List<UserPost>> entry : topicMap.entrySet()) {
            outputList.addAll(entry.getValue());
        }
        switch(SortOrder){
            case ASC:
                return outputList;
            case DESC:
                Collections.reverse(outputList);
                return outputList;
            default:
                return null;
        }
    }

    private NavigableMap<String, Integer> getKeywords(String userContent) {
        String[] userWords = userContent.replaceAll("[^\\w ]", "").toLowerCase().split(" ");
        NavigableMap<String, Integer> wordMap = new TreeMap<>();
        for (String word : userWords) {
            Integer wordCount = wordMap.get(word);
            if(wordCount == null){wordCount = 1;
            }else{wordCount++;
            }
            wordMap.put(word, wordCount);
        }
        return wordMap;
    }

    private void addToList(Map<String, Integer> keywordMap, UserPost userPost) {
        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : keywordMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        if (!topicMap.containsKey(maxEntry.getKey())) {
            topicMap.put(maxEntry.getKey(), new ArrayList<>());
        }
        topicMap.get(maxEntry.getKey()).add(userPost);
    }
}
