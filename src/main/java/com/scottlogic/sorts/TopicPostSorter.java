package com.scottlogic.sorts;

import com.scottlogic.KeywordExtract;
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
            Map.Entry<String, Integer> maxEntry = new KeywordExtractor().extract(userPost.getContents());
            if (!topicMap.containsKey(maxEntry.getKey())) {
                topicMap.put(maxEntry.getKey(), new ArrayList<>());
            }
            topicMap.get(maxEntry.getKey()).add(userPost);
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
}
