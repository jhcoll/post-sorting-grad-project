package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;

public class LikePostFilter implements PostFilter {
    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> outputList = new ArrayList<>(inputList);
        outputList.removeIf(a -> (a.getLikeCount() <= 0));
        return outputList;
    }
}
