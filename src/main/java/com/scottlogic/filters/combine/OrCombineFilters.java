package com.scottlogic.filters.combine;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OrCombineFilters implements PostFilter {
    final private PostFilter filter1;
    final private PostFilter filter2;

    public OrCombineFilters(PostFilter inputFilter1, PostFilter inputFilter2){
        filter1 = inputFilter1;
        filter2 = inputFilter2;
    }


    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if(inputList == null){return null;}
        List<UserPost> outputList2 = filter1.filter(inputList);
        List<UserPost> outputList3 = filter2.filter(inputList);

        List<UserPost> outputList = new ArrayList<>(outputList2);
        outputList.addAll(outputList3);

        final Set<UserPost> s = new LinkedHashSet<>(outputList);
        outputList.clear();
        outputList.addAll(s);

        return outputList;
    }
}
