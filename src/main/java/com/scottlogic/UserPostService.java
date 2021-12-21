package com.scottlogic;

import com.scottlogic.filters.combine.AndCombineFilters;
import com.scottlogic.filters.combine.OrCombineFilters;

import java.util.List;

public class UserPostService {
    private static List<UserPost> userPostList;

    public UserPostService(List<UserPost> userPostList) {
        UserPostService.userPostList = userPostList;
    }

    public List<UserPost> postSort(PostSorter postSorter, SortOrder sortOrder) {
        return postSorter.sort(userPostList, sortOrder);
    }

    public List<UserPost> postFilter(PostFilter postFilter) {
        return postFilter.filter(userPostList);
    }

    public List<UserPost> filterSorter(PostFilter postFilter, PostSorter postSorter, SortOrder sortOrder) {
        List<UserPost> outputList = postFilter.filter(userPostList);
        return postSorter.sort(outputList, sortOrder);
    }

    public List<UserPost> andFilter(PostFilter filter1, PostFilter filter2) {
        final AndCombineFilters andCombineFilters = new AndCombineFilters(filter1, filter2);
        return andCombineFilters.filter(userPostList);
    }

    public List<UserPost> orFilter(PostFilter filter1, PostFilter filter2) {
        final OrCombineFilters orCombineFilters = new OrCombineFilters(filter1, filter2);
        return orCombineFilters.filter(userPostList);
    }
}
