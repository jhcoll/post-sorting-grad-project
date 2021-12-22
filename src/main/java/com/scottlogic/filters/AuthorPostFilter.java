package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorPostFilter implements PostFilter {

    private final String author;

    public AuthorPostFilter(String author) {
        this.author = author;
    }

    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        if (Objects.equals(author, "")) {
            return new ArrayList<>();
        }
        List<UserPost> outputList = new ArrayList<>(inputList);
        outputList.removeIf(a -> !a.getAuthor().contains(author));
        return outputList;
    }
}
