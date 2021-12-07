package com.scottlogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AuthorPostSorter implements PostSorter {

    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList != null) {
            List<UserPost> copyInputList = new ArrayList<>(inputList);
            copyInputList.sort(Comparator.comparing(UserPost::getAuthor));
            if (SortOrder == com.scottlogic.SortOrder.DESC) {
                copyInputList.sort(Collections.reverseOrder(Comparator.comparing(UserPost::getAuthor)));
            }
            return copyInputList;
        }
        return null;
    }
}
