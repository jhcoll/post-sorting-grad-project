package com.scottlogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LikesPostSorter implements PostSorter {
    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList != null) {
            List<UserPost> copyInputList = new ArrayList<>(inputList);
            copyInputList.sort(Comparator.comparing(UserPost::getLikeCount));
            if (SortOrder == com.scottlogic.SortOrder.DESC) {
                copyInputList.sort(Collections.reverseOrder(Comparator.comparing(UserPost::getLikeCount)));
            }
            return copyInputList;
        }
        return null;
    }
}
