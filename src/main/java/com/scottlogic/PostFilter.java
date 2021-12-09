package com.scottlogic;

import java.util.List;

public interface PostFilter {
    List<UserPost> filter(List<UserPost> inputList);
}
