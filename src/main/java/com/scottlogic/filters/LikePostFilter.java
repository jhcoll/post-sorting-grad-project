package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LikePostFilter implements PostFilter {
    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> outputList = new ArrayList<>(inputList);
        return outputList
                .stream()
                .filter(a -> (a.getLikeCount() >= 1))
                .collect(Collectors.toList());
    }
}
