package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorPostFilter implements PostFilter {

    final String Author;

    public AuthorPostFilter(String authorInput) {
        Author = authorInput;
    }

    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        if(Objects.equals(Author, "")){return new ArrayList<>();}
        List<UserPost> outputList = new ArrayList<>(inputList);
        outputList.removeIf(a -> !a.getAuthor().contains(Author));
        return outputList;
    }
}
