package com.scottlogic.filters.combine;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.List;

public class OrCombineFilters implements PostFilter {
    final private PostFilter filter1;
    final private PostFilter filter2;

    public OrCombineFilters(PostFilter inputFilter1, PostFilter inputFilter2) {
        filter1 = inputFilter1;
        filter2 = inputFilter2;
    }


    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> outputList2 = new ArrayList<>(filter1.filter(inputList));
        List<UserPost> outputList3 = new ArrayList<>(filter2.filter(inputList));

        List<UserPost> outputList = new ArrayList<>(outputList2);

        List<UserPost> duplicateList = new AndCombineFilters(filter1, filter2).filter(inputList);
        outputList3.removeIf(duplicateList::contains);

        outputList.addAll(outputList3);

        return outputList;
    }
}
