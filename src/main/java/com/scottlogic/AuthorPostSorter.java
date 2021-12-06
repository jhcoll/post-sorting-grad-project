package com.scottlogic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AuthorPostSorter implements PostSorter {

    @Override
    public List<UserPost> sort(List<UserPost> inputList) {
        if(inputList != null){
                inputList.sort(Comparator.comparing(UserPost::getAuthor));
        }
        return inputList;
    }
}
