package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatePostFilter implements PostFilter {
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    public DatePostFilter(OffsetDateTime startDateInput, OffsetDateTime endDateInput) {
        this.startDate = startDateInput;
        this.endDate = endDateInput;
    }

    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> outputList = new ArrayList<>(inputList);
        outputList.removeIf(a -> (a.getDateTime().compareTo(startDate) <= 0));
        outputList.removeIf(a -> (a.getDateTime().compareTo(endDate) >= 0));
        return outputList;
    }
}
