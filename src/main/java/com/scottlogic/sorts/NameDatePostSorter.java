package com.scottlogic.sorts;

import com.scottlogic.PostSorter;
import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;

import java.util.List;
import java.util.stream.Collectors;

public class NameDatePostSorter implements PostSorter {

    public final DatePostSorter DatePostSorter;
    public final AuthorPostSorter AuthorPostSorter;

    public NameDatePostSorter(DatePostSorter DatePostSorter, AuthorPostSorter AuthorPostSorter) {
        this.DatePostSorter = DatePostSorter;
        this.AuthorPostSorter = AuthorPostSorter;
    }

    @Override
    public List<UserPost> sort(List<UserPost> inputList, SortOrder SortOrder) {
        if (inputList == null) {
            return null;
        }
        if (SortOrder == null) {
            return null;
        }
        List<UserPost> outputList = DatePostSorter.sort(inputList, SortOrder);
        return AuthorPostSorter.sort(outputList, SortOrder).stream().collect(Collectors.toList());
    }
}
