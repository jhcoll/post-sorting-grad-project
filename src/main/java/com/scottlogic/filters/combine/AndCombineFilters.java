package com.scottlogic.filters.combine;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;
import com.scottlogic.filters.AuthorPostFilter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AndCombineFilters implements PostFilter {
    final private PostFilter filter1;
    final private PostFilter filter2;

    public AndCombineFilters(PostFilter inputFilter1, PostFilter inputFilter2){
        filter1 = inputFilter1;
        filter2 = inputFilter2;
    }


    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if(inputList == null){return null;}
        List<UserPost> outputList = filter1.filter(inputList);
        outputList = filter2.filter(outputList);
        return outputList;
    }
}
