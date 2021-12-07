package com.scottlogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AuthorPostSorter implements PostSorter {
    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList == null) {return null;}
        List<UserPost> outputList = new ArrayList<>(inputList);
        if (SortOrder == com.scottlogic.SortOrder.DESC) {
            outputList.sort(Collections.reverseOrder(Comparator.comparing(a -> a.getAuthor().split(" ")[a.getAuthor().split(" ").length - 1])));
        } else {
            outputList.sort(Comparator.comparing(a -> a.getAuthor().split(" ")[a.getAuthor().split(" ").length - 1]));
        }
        return outputList;
    }
}
