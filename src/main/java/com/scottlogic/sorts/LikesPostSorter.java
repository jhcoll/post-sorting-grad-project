package com.scottlogic.sorts;

import com.scottlogic.PostSorter;
import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LikesPostSorter implements PostSorter {
    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> outputList = new ArrayList<>(inputList);

        switch(SortOrder){
            case ASC:
                return outputList
                        .stream()
                        .sorted(
                                Comparator.comparing(UserPost::getLikeCount))
                        .collect(Collectors.toList());
            case DESC:
                return outputList
                        .stream()
                        .sorted(Collections.reverseOrder(
                                Comparator.comparing(UserPost::getLikeCount)))
                        .collect(Collectors.toList());
            default:
                return null;
        }
    }
}
