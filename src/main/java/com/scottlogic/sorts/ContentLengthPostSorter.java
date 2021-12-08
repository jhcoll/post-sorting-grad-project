package com.scottlogic.sorts;

import com.scottlogic.PostSorter;
import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContentLengthPostSorter implements PostSorter {
    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList == null) {return null;}
        List<UserPost> outputList = new ArrayList<>(inputList);
        if (SortOrder == com.scottlogic.SortOrder.DESC) {
            outputList.sort(Collections.reverseOrder(Comparator.comparingInt(a -> a.getContents().length())));
        } else {
            outputList.sort(Comparator.comparingInt(a -> a.getContents().length()));
        }
        return outputList;
    }
}
