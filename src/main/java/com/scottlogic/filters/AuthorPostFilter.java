package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuthorPostFilter implements PostFilter {

    private final String Author;

    public AuthorPostFilter(String authorInput) {
        this.Author = authorInput;
    }

    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        if (Objects.equals(Author, "")) {
            return new ArrayList<>();
        }
        List<UserPost> outputList = new ArrayList<>(inputList);
        return outputList
                .stream()
                .filter(a -> a.getAuthor().contains(Author))
                .collect(Collectors.toList());
    }
}
